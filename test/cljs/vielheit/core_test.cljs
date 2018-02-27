(ns vielheit.core-test
  (:require [cljs.test :refer-macros [is are deftest testing use-fixtures async]]
            [pjstadig.humane-test-output]
            [clojure.string :refer [blank?]]
            [vielheit.core :as vc]
            [re-frame.core :as re-frame]))

(deftest test-initial-login-state
  (testing "has the correct state without a session"
    (let [submitting @(re-frame/subscribe [:page-login/submitting])
          error      @(re-frame/subscribe [:page-login/error])
          email      @(re-frame/subscribe [:page-login/email])
          pass       @(re-frame/subscribe [:page-login/pass])]

      (is (not submitting))
      (is (nil? error))
      (is (blank? email))
      (is (blank? pass)))))

