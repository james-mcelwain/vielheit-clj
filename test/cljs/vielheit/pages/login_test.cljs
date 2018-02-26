(ns vielheit.pages.login-test
  (:require [vielheit.pages.login :as sut]
            [re-frame.core :as re-frame]
            [cljs.test :as t :include-macros true]))

(t/deftest test-initial-login-state
  (t/testing "has the correct state without a session"
    (let [submitting @(re-frame/subscribe [:page-login/submitting])
          error      @(re-frame/subscribe [:page-login/error])
          email      @(re-frame/subscribe [:page-login/email])
          pass       @(re-frame/subscribe [:page-login/pass])

          logged-in  @(re-frame/subscribe [:logged-in])
          user       @(re-frame/subscribe [:user])]

      (t/is (not submitting))
      (t/is (nil? error))
      (t/is (blank? email))
      (t/is (blank? pass))

      (t/is (not logged-in))
      (t/is (nil? user)))))


(t/deftest test-local-storage-login-state
  (t/testing "has the correct state without a session"
    (.setItem js/localStorage "token" "1234")
    (let [submitting @(re-frame/subscribe [:page-login/submitting])
          error      @(re-frame/subscribe [:page-login/error])
          email      @(re-frame/subscribe [:page-login/email])
          pass       @(re-frame/subscribe [:page-login/pass])]

      (t/is (not submitting))
      (t/is (nil? error))
      (t/is (blank? email))
      (t/is (blank? pass)))))

