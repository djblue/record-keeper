(ns record-keeper.core
  (:require [clojure.string :as s]))

(defn- sortable-date [date-string]
  (let [[M D Y]
        (map read-string (s/split date-string #"\/"))]
    [Y M D]))

(defn records-by-gender [records]
  (sort-by (juxt :gender :last-name) records))

(defn records-by-birth-date [records]
  (sort-by (comp sortable-date :date-of-birth) records))

(defn records-by-last-name [records]
  (sort-by :last-name #(* -1 (compare %1 %2)) records))
