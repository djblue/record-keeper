(ns record-keeper.server-test
  (:require [record-keeper.server :refer [handler]]
            [clojure.test :refer [deftest are]]))

(deftest not-found
  (are [method uri status]
       (= (:status (handler {:uri uri :request-method method}))
          status)
    :get "/" 404
    :get "/records/gender" 200
    :get "/records/birthdate" 200
    :get "/records/name" 200
    :post "/records" 400))

