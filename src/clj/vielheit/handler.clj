(ns vielheit.handler
  (:require [compojure.core :refer [routes wrap-routes context]]
            [vielheit.layout :refer [error-page]]
            [vielheit.routes.home :refer [home-routes]]
            [vielheit.routes.api.core :refer [api-routes]]
            [compojure.route :as route]
            [vielheit.env :refer [defaults]]
            [mount.core :as mount]
            [vielheit.middleware :as middleware]))

(mount/defstate init-app
                :start ((or (:init defaults) identity))
                :stop  ((or (:stop defaults) identity)))

(def app-routes
  (routes
   (-> #'home-routes
       (wrap-routes middleware/wrap-csrf)
       (wrap-routes middleware/wrap-formats))
   (context "/api" []
            (-> #'api-routes
                (wrap-routes middleware/wrap-csrf)
                (wrap-routes middleware/wrap-formats)
                (wrap-routes middleware/wrap-restricted)))
   (route/not-found
    (:body
     (error-page {:status 404
                  :title "page not found"})))))


(defn app [] (middleware/wrap-base #'app-routes))
