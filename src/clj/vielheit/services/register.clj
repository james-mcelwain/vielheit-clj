(ns vielheit.services.register
  (:require
   [clojure.tools.logging :as log]
   [vielheit.db.core :as db]
   [vielheit.services.auth :as auth]
   [vielheit.util.error :as error]
   [ring.util.http-response :as response]))

(defn- error-unique? [e]
  (clojure.string/includes? (-> e .getCause .getMessage) "uq_email"))

(defn register [user]
  (try
    (db/create-user! (auth/encrypt-password user))
    (catch Exception e
      (case
          (error-unique? e) (do
                              (log/error (str "email exists " (:email user)) e)
                              (error/error (response/bad-request {:error "email exists"
                                                                  :field :email
                                                                  :value (:email user)})))))))
