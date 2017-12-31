(ns vielheit.routes.register
  (:require
   [clojure.tools.logging :as log]
   [compojure.core :refer [defroutes POST]]
   [vielheit.db.core :as db]
   [vielheit.services.auth :as auth]
   [ring.util.http-response :as response]
   [vielheit.util.error :refer [unwrap*]]
   [vielheit.services.register :as register]))

(defn- error-unique? [e]
  (clojure.string/includes? (-> e .getCause .getMessage) "uq_email"))

(defn- handle-register-error [e user]
  (case
      {}(error-unique? e)
    (do
      (log/error (str "email exists " (:email user)) e)
      (response/bad-request {:error "email exists"
                             :field :email
                             :value (:email user)}))))

(defroutes register-routes
  (POST "/register" {user :params}
        (try
          (response/ok (db/create-user! (auth/encrypt-password user)))
          (catch Exception e (handle-register-error e user)))))

