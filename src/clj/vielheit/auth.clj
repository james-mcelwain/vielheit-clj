(ns vielheit.auth
  (:require
   [vielheit.db.core :as db]
   [buddy.hashers :as hashers]))

(defn password-matches? [email password]
  (if-let [user (db/get-user-by-email {:email email})]
    (do
      (println (hash-password password) (:pass user))
      (hashers/check password (:pass user)))
    false))

(defn hash-password [password]
  (hashers/derive password))

(defn encrypt-password [user]
  (assoc user :pass (hash-password (:pass user))))

