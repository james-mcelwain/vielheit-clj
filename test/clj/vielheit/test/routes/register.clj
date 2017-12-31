(ns vielheit.test.routes.register
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [vielheit.handler :refer :all]))

(deftest test-registration
  (testing "registration")
  (let [user {:first_name "Sam"
              :last_name  "Smith"
              :email      "sam.smith@example.com"
              :pass       "pass"}
        response ((app) (json-body (request :post "/register") user))]
    (is (= 200 (:status response)))))

