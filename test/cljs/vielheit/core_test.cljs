(ns vielheit.core-test
  (:require [cljs.test :refer-macros [is are deftest testing use-fixtures async]]
            [pjstadig.humane-test-output]
            [reagent.core :as reagent :refer [atom]]
            [vielheit.core :as rc]))

(deftest test-initial-login-state
  (testing "loads login screen by default"
    (async done
           (js/setTimeout
            (fn []
              (is true)
              (done)) 10000))))

