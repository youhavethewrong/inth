(defproject inth "0.1.0-SNAPSHOT"
  :description "A library for indexing URLs"
  :url "https://github.com/youhavethewrong/inth"
  :license {:name "Apache License, Version 2.0"
            :url "https://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-http "0.9.2"]
                 [enlive "1.1.5"]
                 [org.clojure/java.jdbc "0.3.3"]
                 [mysql/mysql-connector-java "5.1.30"]]
  :aot :all
  :main inth.core
  :uberjar-name "inth.jar")
