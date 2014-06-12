(ns inth.db
  (:require [clojure.java.jdbc :as sql]))

(def mysql-db {:subprotocol "mysql"
               :subname "//bastion.youhavethewrong.info:3306/hn" 
               :user "hn"
               :password ""})

(defn show-articles
  []
  (println (sql/query mysql-db ["SELECT * FROM article"])))

(defn insert-article
  [title url]
  (sql/insert! mysql-db :article {:title title :url url}))

(defn bulk-insert
  [links]
  (println (str "Inserting: " links))
  (if (not (empty? links))
    (map (fn [link] (insert-article (:title link) (:link link))) links)))
  
