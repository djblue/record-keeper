(ns record-keeper.format-test
  (:require [clojure.java.io :as io]
            [record-keeper.format :as fmt]
            [clojure.test :refer [deftest are]]))

(defn get-sample-data []
  (read-string (slurp (io/resource "sample-data.edn"))))

(def data (get-sample-data))

(deftest format-inversion
  (are [delimiter]
       (= data (-> data
                   (fmt/write-str delimiter)
                   (fmt/read-str delimiter)))
    ::fmt/space
    ::fmt/comma
    ::fmt/pipe))

