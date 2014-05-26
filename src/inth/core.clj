(ns inth.core
  (:require [clj-http.client :as client]))

(defn retrieve-url
  "Retrieves the contents of a URL"
  [url]
  (:body (client/get url)))
