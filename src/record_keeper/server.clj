(ns record-keeper.server
  (:require [clojure.data.json :as json]
            [record-keeper.core :as rk]
            [record-keeper.format :as fmt]
            [ring.adapter.jetty :refer [run-jetty]]))

(def db (atom []))

(defn as-json [status data]
  {:status status
   :headers {"Content-Type" "application/json"}
   :body (json/write-str data)})

(defn handler [req]
  (case [(:request-method req) (:uri req)]
    [:post "/records"]
    (try
      (as-json 201 (swap! db concat (fmt/read-str (slurp (:body req)))))
      (catch Exception e
        (as-json 400
                 (assoc (ex-data e) :message (.getMessage e)))))

    [:get "/records/gender"]    (as-json 200 (rk/records-by-gender @db))
    [:get "/records/birthdate"] (as-json 200 (rk/records-by-birth-date @db))
    [:get "/records/name"]      (as-json 200 (rk/records-by-last-name @db))

    {:status 404
     :headers {"Content-Type" "text/plain"}
     :body "Not Found"}))

(defn -main []
  (run-jetty #'handler {:port 3000}))

