(ns record-keeper.format
  "Functions for dealing with record data formats."
  (:require [clojure.string :as s]))

(def ^:private record-keys
  [:last-name :first-name :gender :favorite-color :date-of-birth])

;; Record Reader

(def ^:private read-delimiters
  {::space #" " ::comma #", " ::pipe #" \| "})

(defn- read-delimited [string delimiter]
  (->> (zipmap
        record-keys
        (s/split string (get read-delimiters delimiter)))
       (filter (fn [[k v]] (not= v "")))
       (into {})))

(defn read-str
  "Read record string of the following format:
  LastName | FirstName | Gender | FavoriteColor | DateOfBirth
  NOTE: empty columns will not be included in parsed values."
  [string delimiter]
  (map #(read-delimited % delimiter) (s/split string #"\n")))

;; Record Writer

(def ^:private write-delimiters
  {::space " " ::comma ", " ::pipe " | "})

(defn- write-delimited [record delimiter]
  (->> record-keys
       (map #(get record %))
       (s/join (get write-delimiters delimiter))))

(defn write-str
  "Writes record strings with using the following keys:
  :last-name :first-name :gender :favorite-color :date-of-birth"
  [records delimiter]
  (s/join "\n" (map #(write-delimited % delimiter) records)))

