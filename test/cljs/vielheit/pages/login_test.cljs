(ns vielheit.pages.login-test
  (:require
   [vielheit.core :as vh]
   [vielheit.pages.login :as sut]
   [re-frame.core :as re-frame]
   [clojure.string :as s]
   [re-frame.db :as db]
   [cljs.test :as t :include-macros true]))

(t/deftest test-initial-login-state
  (t/testing "has the correct state without a session"
    (re-frame/dispatch-sync [:initialize-db])
    (let [submitting @(re-frame/subscribe [:page-login/submitting])
          error      @(re-frame/subscribe [:page-login/error])
          email      @(re-frame/subscribe [:page-login/email])
          pass       @(re-frame/subscribe [:page-login/pass])

          logged-in  @(re-frame/subscribe [:logged-in])
          user       @(re-frame/subscribe [:user])]

      (t/is (not submitting))
      (t/is (nil? error))
      (t/is (s/blank? email))
      (t/is (s/blank? pass))

      (t/is (not logged-in))
      (t/is (nil? user)))))


(t/deftest test-local-storage-login-state
  (t/testing "has the correct state without a session"
    (.setItem js/localStorage "token" "1234")
    (re-frame/dispatch-sync [:initialize-db])
    (let [submitting @(re-frame/subscribe [:page-login/submitting])
          error      @(re-frame/subscribe [:page-login/error])
          email      @(re-frame/subscribe [:page-login/email])
          pass       @(re-frame/subscribe [:page-login/pass])

          logged-in  @(re-frame/subscribe [:logged-in])
          user       @(re-frame/subscribe [:user])]

      (t/is (not submitting))
      (t/is (nil? error))
      (t/is (s/blank? email))
      (t/is (s/blank? pass))

      (t/is logged-in)
      (t/is (nil? user)))))
