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

(deftest format-detection
  (are [delimiter]
       (= delimiter
          (-> data
              (fmt/write-str delimiter)
              fmt/detect-delimiter))
    ::fmt/space
    ::fmt/comma
    ::fmt/pipe))

(deftest bad-record-format
  (are [input]
       (thrown-with-msg? Exception #"Unknown record format"
                         (fmt/read-str input))
    "grep"
    "")

  (are [input]
       (thrown-with-msg? Exception #"Malformed record"
                         (doall (fmt/read-str input)))
    "a | b"
    "a | b | c | d"
    "a, b"
    "a, b, c, d"
    "a b c d"))

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

