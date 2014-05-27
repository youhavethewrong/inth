(ns inth.core
  (:require [clojure.string :as string]
            [clj-http.client :as client]
            [net.cgrand.enlive-html :as html]))

(defn retrieve-url
  "Retrieves the contents of a URL."
  [url]
  (:body (client/get url)))

(defn find-links
  "Extracts the reference and text of a link in a body of HTML content."
  [content]
  (map (fn
         [x]
         (let [title (first (:content x))]
           (assoc {}
             :title (if
                        (or
                         (not
                          (string? title))
                         (string/blank? title))
                      "no title"
                      title)
             :link ((:attrs x) :href))))
       (html/select
        (html/html-resource
         (java.io.StringReader. content))
        [:a])))

(defn filter-relative
  "Filters out relative links."
  [links]
  (filter (fn
            [link]
            (.startsWith
             (string/lower-case (:link link))
             "http")) links))


(defn find-related-title
  "Finds links with titles similar to a string."
  [desired links]
  (filter (fn
            [link]
            (.contains
             (string/lower-case (:title link))
             (string/lower-case desired)))
          links))
