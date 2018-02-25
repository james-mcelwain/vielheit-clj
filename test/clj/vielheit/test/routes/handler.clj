(ns vielheit.test.routes.register
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [vielheit.handler :refer :all]
            [vielheit.test.util.user :refer [random-user]]))

(deftest test-registration
  (testing "registration")
  (let [user     (random-user)
        response ((app) (json-body (request :post "/register") user))]
    (is (= 200 (:status response)))))
