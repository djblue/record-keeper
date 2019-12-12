(ns record-keeper.core-test
  (:require [record-keeper.core :as rk]
            [clojure.test :refer [deftest are]]))

(def sort-data
  [{:id 0
    :gender "male"
    :last-name "A"
    :date-of-birth "2/2/2222"}
   {:id 1
    :gender "female"
    :last-name "C"
    :date-of-birth "3/3/3333"}
   {:id 2
    :gender "female"
    :last-name "B"
    :date-of-birth "1/1/1111"}])

(deftest record-sorting
  (are [sorter result]
       (= (->> sort-data sorter (map :id)) result)
    rk/records-by-gender [2 1 0]
    rk/records-by-last-name [1 2 0]
    rk/records-by-birth-date [2 0 1]))
