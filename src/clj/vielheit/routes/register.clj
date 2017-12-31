(ns vielheit.routes.register
  (:require
   [compojure.core :refer [defroutes POST]]
   [ring.util.http-response :as response]
   [vielheit.util.error :refer [unwrap*]]
   [vielheit.services.register :as register]))

(defroutes register-routes
  (POST "/register" {user :params}
        (unwrap* (register/register user))))
