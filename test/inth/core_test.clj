(ns inth.core-test
  (:require [clojure.test :refer :all]
            [inth.core :refer :all]))

(deftest get-test
  (testing "Should successfully retrieve content from URL."
    (is (= 1154
           (.length
            (inth.core/retrieve-url "http://ecik.youhavethewrong.info"))))))
