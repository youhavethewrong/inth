(ns inth.core-test
  (:require [clojure.test :refer :all]
            [inth.core :refer :all]))

(deftest get-test
  (testing "Should successfully retrieve content from URL."
    (is (= 1154
           (.length
            (inth.core/retrieve-url "http://ecik.youhavethewrong.info"))))))


(deftest count-links
  (testing "Should find 3 links within the body of the retrieved URL"
    (is (= 3
           (count
            (inth.core/find-links
             (inth.core/retrieve-url "http://ecik.youhavethewrong.info")) )))))
