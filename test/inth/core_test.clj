(ns inth.core-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [inth.core :refer :all]))

(def local-file (io/file
                (io/resource
                 "hn.html")))

(def link-ball (inth.core/find-links
              (slurp local-file)))

(deftest get-test
  (testing "Should successfully retrieve content via HTTP"
    (is (= 1495
           (.length
            (inth.core/retrieve-url "http://ecik.youhavethewrong.info"))))))

(deftest count-hn-links
  (testing "Should find n links within the body of the local data file."
    (is (= 139
           (count link-ball)))))

(deftest filter-relative-links
  (testing "Should filter out relative links."
    (is (= 39
           (count
            (inth.core/filter-links
             inth.core/find-prefix
             link-ball
             ["http" "ftp"]))))))

(deftest find-clojure-links
  (testing "Should find interesting articles."
    (is (= 1
           (count
            (inth.core/filter-links
             inth.core/find-related-title
             link-ball
             ["clojure"]))))))

(deftest find-many-interesting-links
  (testing "Should find interesting articles on a variety of topics."
    (is (= 2
           (count
            (inth.core/filter-links
             inth.core/find-related-title
             link-ball
             ["clojure" "prefix"]))))))

(deftest find-patterns
  (testing "Should find articles via regex patterns."
    (is (= 11
           (count
            (inth.core/filter-links
             inth.core/find-regex-title
             link-ball
             [#"\b[T|t]he\b" #"\b[L|l]earn(:?ing)?\b"]))))))
