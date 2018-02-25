(ns vielheit.test.routes.api.core
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [vielheit.handler :refer :all]))


(deftest test-auth-fail
  (testing "authentication fail")
  (let [response ((app) (request :get "/api/check"))]
    (is (= 403 (:status response)))))

