(ns vielheit.auth
  (:require
   [vielheit.db.core :as db]
   [buddy.hashers :as hashers]))

(defn hash-pass [pass]
  (hashers/derive pass))

(defn pass-matches? [email pass]
  (if-let [user (db/get-user-by-email {:email email})]
    (do
      (hashers/check pass (:pass user)))
    false))

(defn encrypt-pass [user]
  (assoc user :pass (hash-pass (:pass user))))

