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
  (try
    (sql/insert! mysql-db :article {:title title :url url})
    (catch com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e (println (str "Already inserted " url)))))

(defn bulk-insert
  [links]
  (println (str "Inserting " (count links) " links"))
  (if (not (empty? links))
    (doall (map
            (fn
              [link]
              (insert-article
               (:title link)
               (:link link)))
            links)))) 
  
