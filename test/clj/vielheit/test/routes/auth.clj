(ns vielheit.test.routes.auth
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [vielheit.handler :refer :all]))

(deftest test-jwe
  (testing "jwe")
  (let [user {:first_name "Sam"
              :last_name  "Smith"
              :email      "sam.smith@example.com"
              :pass       "pass"}
        reg-res  ((app) (json-body (request :post "/register") user))
        auth-res ((app) (json-body (request :get "/authenticate") user))]
    (is (= 200 (:status reg-res)))
    (is (= 200 (:status auth-res)))))

