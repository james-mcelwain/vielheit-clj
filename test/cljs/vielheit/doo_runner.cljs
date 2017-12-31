(ns vielheit.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [vielheit.core-test]))

(doo-tests 'vielheit.core-test)

