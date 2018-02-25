(ns vielheit.test.util.user
  (:require [faker.name :as name]
            [faker.internet :as net]
            [crypto.random :as random]))

(defn random-user []
  {:first_name (name/first-name)
   :last_name  (name/last-name)
   :email      (str (random/hex 6) (net/email))
   :pass       "pass" hex
   :admin nil
   :last_login nil
   :is_active nil})
