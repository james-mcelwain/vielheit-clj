(ns vielheit.test.util.user
  (:require [faker.name :as name]
            [faker.internet :as net]))

(defn random-user []
  {:first_name (name/first-name)
   :last_name  (name/last-name)
   :email      (net/email)
   :pass       "pass"
   :admin nil
   :last_login nil
   :is_active nil})
