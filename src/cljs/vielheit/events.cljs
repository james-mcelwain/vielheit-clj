(ns vielheit.events
  (:require [vielheit.db :as db]
            [re-frame.core :refer [dispatch reg-event-db reg-sub]]))

;;dispatchers
(reg-event-db
 :log-in
 (fn [db [_ user]]
   (assoc db :logged-in true :user user)))

(reg-event-db
 :log-out
 (fn [db [_ _]]
   (assoc db :logged-in false :user nil)))

(reg-event-db
  :initialize-db
  (fn [_ _]
    db/default-db))

(reg-event-db
  :set-active-page
  (fn [db [_ page]]
    (assoc db :page page)))

;;subscriptions
(reg-sub
 :logged-in
 (fn [db _]
   (:logged-in db)))

(reg-sub
  :page
  (fn [db _]
    (:page db)))
