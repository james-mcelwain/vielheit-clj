(ns vielheit.routes.api.core
  (:require
   [compojure.core :refer [defroutes GET]]
   [ring.util.http-response :as response]))

(defroutes api-routes
  (GET "/check" []
       (response/ok)))

