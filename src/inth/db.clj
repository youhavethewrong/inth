(ns inth.db
  (:require [clojure.java.jdbc]))

(def mysql-db {:subprotocol "mysql"
               :subname "//bastion.youhavethewrong.info:3306/hn"
               :user "hn"
               :password ""})

(defn show-articles
  []
  (println (clojure.java.jdbc/query mysql-db ["SELECT * FROM article"])))
