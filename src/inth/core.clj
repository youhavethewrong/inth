(ns inth.core
  (:require [inth.db]
            [clojure.string :as string]
            [clj-http.client :as client]
            [net.cgrand.enlive-html :as html])
  (:gen-class :main true))

(defn retrieve-url
  "Retrieves the contents of a URL."
  [url]
  (:body (client/get url)))

(defn find-links
  "Extracts the reference and text of a link in a body of HTML content."
  [content]
  (map (fn
         [anchor]
         (let [title (first (:content anchor))]
           (assoc {}
             :title (if
                        (or
                         (not
                          (string? title))
                         (string/blank? title))
                      "no title"
                      title)
             :link ((:attrs anchor) :href))))
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
  "Finds links with titles which contain any interesting words."
  [desired links]
  (filter (fn
            [link]
            (some #(.contains (string/lower-case (:title link))
                              (string/lower-case %))
                  desired))
          links))

(defn -main [& args]
  "Insert any found articles into the configured database."
  (inth.db/bulk-insert
   (find-related-title
    (rest args)
    (find-links
     (retrieve-url
      (first args))))))
