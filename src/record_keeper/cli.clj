(ns record-keeper.cli
  (:require [clojure.pprint :as p]
            [record-keeper.core :as rk]
            [record-keeper.format :as fmt]))

(def ^:private record-keys
  [:last-name :first-name :gender :favorite-color :date-of-birth])

(defn read-files [files]
  (->> files
       (map slurp)
       (map fmt/read-str)
       (apply concat)))

(def sorts
  {"gender"    rk/records-by-gender
   "birthdate" rk/records-by-birth-date
   "name"      rk/records-by-last-name})

(defn sorted-view [sort records]
  (let [f (get sorts sort)]
    (if (fn? f)
      (p/print-table record-keys (f records))
      (do (println (str "Unknown sort \"" sort "\""))
          (System/exit 1)))))

(defn -main [sort & files]
  (sorted-view sort (read-files files)))

