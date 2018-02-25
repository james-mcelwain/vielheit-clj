(ns vielheit.test.routes.auth
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [vielheit.handler :refer :all]
            [vielheit.test.util.user :refer [random-user]]))

(deftest test-jwe
  (testing "jwe")
  (let [user     (random-user)
        reg-res  ((app) (json-body (request :post "/register") user))
        auth-res ((app) (json-body (request :get "/authenticate") user))]
    (is (= 200 (:status reg-res)))
    (is (= 200 (:status auth-res)))))

