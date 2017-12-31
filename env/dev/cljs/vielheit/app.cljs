(ns ^:figwheel-no-load vielheit.app
  (:require [vielheit.core :as core]
            [devtools.core :as devtools]))

(enable-console-print!)

(devtools/install!)

(core/init!)
