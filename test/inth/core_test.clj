(ns inth.core-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [inth.core :refer :all]))

(def local-file (io/file
                (io/resource
                 "hn.html")))
(deftest get-test
  (testing "Should successfully retrieve content via HTTP"
    (is (= 1154
           (.length
            (inth.core/retrieve-url "http://ecik.youhavethewrong.info"))))))

(deftest count-links
  (testing "Should find 3 links within the body of the retrieved URL"
    (is (= 3
           (count
            (inth.core/find-links
             (inth.core/retrieve-url "http://ecik.youhavethewrong.info")) )))))

(deftest count-hn-links
  (testing "Should find n links within the body of the local data file."
    (is (= 139
           (count
            (inth.core/find-links
             (slurp local-file)))))))

(deftest filter-relative-links
  (testing "Should filter out relative links."
    (is (= 39
           (count
            (inth.core/filter-relative
             (inth.core/find-links
              (slurp local-file))))))))

(deftest find-clojure-links
  (testing "Should find interesting articles."
    (is (= 1
           (count
            (inth.core/find-related-title ["clojure"]
                                    (inth.core/find-links
                                     (slurp local-file))))))))

(deftest find-many-interesting-links
  (testing "Should find interesting articles on a variety of topics."
    (is (= 2
           (count
            (inth.core/find-related-title ["clojure" "prefix"]
                                    (inth.core/find-links
                                     (slurp local-file))))))))
