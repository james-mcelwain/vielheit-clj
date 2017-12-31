(ns user
  (:require [luminus-migrations.core :as migrations]
            [vielheit.config :refer [env]]
            [mount.core :as mount]
            [vielheit.figwheel :refer [start-fw stop-fw cljs]]
            vielheit.core))

(defn start []
  (mount/start-without #'vielheit.core/repl-server))

(defn stop []
  (mount/stop-except #'vielheit.core/repl-server))

(defn restart []
  (stop)
  (start))

(defn migrate []
  (migrations/migrate ["migrate"] (select-keys env [:database-url])))

(defn rollback []
  (migrations/migrate ["rollback"] (select-keys env [:database-url])))

(defn create-migration [name]
  (migrations/create name (select-keys env [:database-url])))


