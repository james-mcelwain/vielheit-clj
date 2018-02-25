(ns vielheit.pages.login
  (:require
   [ajax.core :refer [GET POST]]
   [re-frame.core :as re-frame]
   [vielheit.util :refer [?assoc]]))

;; local events

;; dispatchers
(re-frame/reg-event-db
 :page-login/submit
 (fn [db [_ _]]
   (GET "/authenticate" {:handler #(re-frame/dispatch [:page-login/submit-success %])
                         :error-handler #(re-frame/dispatch [:page-login/submit-error %])
                         :params {:email (:page-login/email db)
                                  :pass (:page-login/pass db)}})
   (assoc
    db
    :page-login/error nil
    :page-login/submitting true)))

(re-frame/reg-event-db
 :page-login/submit-error
 (fn [db [_ error]]
   (assoc
    db
    :page-login/error error
    :page-login/submitting false)))

(re-frame/reg-event-db
 :page-login/submit-success
 (fn [db [_ token]]
   (println token)
   (.setItem js/localStorage "token" token)
   (assoc
    db
    :page-login/error nil
    :page-login/submitting false)))

(re-frame/reg-event-db
 :page-login/update-model
 (fn [db [_ {:keys [email pass]}]]
   (?assoc db :page-login/email email :page-login/pass pass)))

;; subscriptions
(re-frame/reg-sub
 :page-login/error
 (fn [db _]
   (:page-login/error db)))

;; page
(defn on-change [evt]
  (.preventDefault evt)
  (let [name (.-name (.-target evt))
        value (.-value (.-target evt))]
    (re-frame/dispatch [:page-login/update-model {(keyword name) value}])))

(defn login-page []
  [:div.login

   ;; errors
   (if-let [error @(re-frame/subscribe [:page-login/error])]
     (cond
       (= (:status error) 403) [:div "Wrong username or pass"]))


   [:h3.login-title "login"]
   [:div.login-title-border]
   [:form.login-form {:on-change on-change :on-submit #(.preventDefault %)}
    [:div
     [:span
      [:label "email:"]
      [:input {:name "email" :type :email}]]]
    [:div
     [:span
      [:label "pass:"]
       [:input {:name "pass" :type :password}]]]
    [:div
     [:button {:on-click #(re-frame/dispatch [:page-login/submit])} "login"]]
    [:div.login-accent-1 "&"]
    [:div.app-name "vielheit"]]])
