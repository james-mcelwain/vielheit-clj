(ns vielheit.routes.api.core
  (:require
   [compojure.core :refer [defroutes GET routes]]
   [ring.util.http-response :as response]
   [vielheit.routes.api.users :as users]))


(def api-routes
  (routes
   users/user-routes
   (GET "/check" []
        (response/ok "OK"))))

