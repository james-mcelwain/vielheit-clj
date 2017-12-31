(ns vielheit.routes.auth
  (:require
   [compojure.core :refer [defroutes GET]]
   [ring.util.http-response :as response]
   [buddy.hashers :as hashers]))

(defroutes auth-routes
  (GET "/authenticate" []))
