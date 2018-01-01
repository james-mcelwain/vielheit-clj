(ns vielheit.routes.auth
  (:require
   [compojure.core :refer [defroutes GET]]
   [ring.util.http-response :as response]
   [vielheit.middleware :as middleware]
   [vielheit.services.auth :as auth]))

(defroutes auth-routes
  (GET "/authenticate" {{email :email pass :pass} :params}
       (println email pass)
       (if (auth/password-matches? email pass)
         (middleware/token email)
         (response/forbidden))))
