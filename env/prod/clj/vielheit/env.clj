(ns vielheit.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[vielheit started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[vielheit has shut down successfully]=-"))
   :middleware identity})
