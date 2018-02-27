(ns vielheit.events
  (:require [vielheit.db :as db]
            [re-frame.core :as rf]))

;;dispatchers
(rf/reg-event-db
 :log-in
 (fn [db [_ user]]
   (assoc db :logged-in true :user user)))

(rf/reg-event-db
 :log-out
 (fn [db [_ _]]
   (assoc db :logged-in false :user nil)))

(rf/reg-event-db
  :initialize-db
  (fn [_ _]
    (db/default-db)))

(rf/reg-event-db
  :set-active-page
  (fn [db [_ page]]
    (assoc db :page page)))

;;subscriptions
(rf/reg-sub
 :logged-in
 (fn [db _]
   (:logged-in db)))

(rf/reg-sub
 :user
 (fn [db _]
   (:user db)))

(rf/reg-sub
  :page
  (fn [db _]
    (:page db)))
