(ns vielheit.routes.api.users
  (:require
   [compojure.core :refer [defroutes GET]]
   [ring.util.http-response :as response]
   [vielheit.middleware :as middleware]
   [vielheit.auth :as auth]))

(defroutes user-routes
  (GET "/user" req
       (response/ok (:session req))))
