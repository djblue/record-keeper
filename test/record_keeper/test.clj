(ns record-keeper.test
  (:require [clojure.java.io :as io]
            [record-keeper.core :as rk]
            [record-keeper.format :as fmt]
            [clojure.test :refer [deftest are run-tests]]))

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

(deftest format-detection
  (are [delimiter]
       (= delimiter
          (-> data
              (fmt/write-str delimiter)
              fmt/detect-delimiter))
    ::fmt/space
    ::fmt/comma
    ::fmt/pipe))

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

(comment
  (defn partition-data [data]
    (->>
     (cycle [::fmt/space ::fmt/comma ::fmt/pipe])
     (map #(-> (assoc %1 :group-by %2)) data)
     (group-by :group-by)))

  (doseq [[delimiter records] (partition-data data)]
    (let [output (fmt/write-str records delimiter)
          file-name (str "resources/example." (name delimiter))]
      (spit file-name output))))

(defn -main [] (run-tests 'record-keeper.test))
