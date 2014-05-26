(ns inth.core
  (:require [clj-http.client :as client]
            [net.cgrand.enlive-html :as html]))

(defn retrieve-url
  "Retrieves the contents of a URL"
  [url]
  (:body (client/get url)))

(defn find-links
  "Extracts the reference and text of a link in a body of HTML content."
  [content]
  (map (fn
         [x]
         ((:attrs x) :href))
       (html/select
        (html/html-resource
         (java.io.StringReader. content))
        [:a])))
