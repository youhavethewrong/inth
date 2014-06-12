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

(defn filter-links
  "Filters the list of link hashes with function f."
  [f links args]
  (filter #(f args %) links))

(defn find-prefix
  "Finds links that begin with a prefix, insensitive to case."
  [prefix link]
  (some #(.startsWith
          (string/lower-case (:link link))
          (string/lower-case %))
        prefix))

(defn find-related-title
  "Finds links with titles which contain any interesting words."
  [desired link]
  (some #(.contains
          (string/lower-case (:title link))
          (string/lower-case %))
        desired))

(defn find-regex-title
  "Finds links with titles which contain regex matches."
  [regex link]
  (some #(not (nil?
               (re-find % (:title link))))
        regex))
  
(defn -main [& args]
  "Insert any found articles into the configured database."
  (inth.db/bulk-insert
   (filter-links
    find-regex-title
    (find-links (retrieve-url (first args)))
    (map (fn
           [pattern]
           (re-pattern pattern))
         (rest args)))))
