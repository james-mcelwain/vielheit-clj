(ns vielheit.test.routes.api.users
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [vielheit.handler :refer :all]
            [vielheit.test.util.user :refer [random-user]]))

(deftest test-auth-user
  (testing "authentication success")
  (let [user     (random-user)
        reg-res  ((app) (json-body (request :post "/register") user))
        auth-res ((app) (json-body (request :get "/authenticate") user))
        response ((app) (header (request :get "/api/user") "Authorization" (str "Token " (:body auth-res))))]
    (is (= 200 (:status response)))))
