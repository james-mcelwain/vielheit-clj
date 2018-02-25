(ns vielheit.test.db.core
  (:require [vielheit.db.core :refer [*db*] :as db]
            [luminus-migrations.core :as migrations]
            [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]
            [vielheit.config :refer [env]]
            [mount.core :as mount]
            [vielheit.test.util.user :refer [random-user]]))

(use-fixtures
  :once
  (fn [f]
    (mount/start
      #'vielheit.config/env
      #'vielheit.db.core/*db*)
    (migrations/migrate ["migrate"] (select-keys env [:database-url]))
    (f)))

(defn map-subset? [a-map b-map]
  (every? (fn [[k _ :as entry]] (= entry (find b-map k))) a-map))

(deftest test-users
  (let [user (random-user)]
    (jdbc/with-db-transaction [t-conn *db*]
    (jdbc/db-set-rollback-only! t-conn)
    (is (= 1 (db/create-user! t-conn user)))
    (is (map-subset? user (db/get-user-by-email t-conn {:email (:email user)}))))))
