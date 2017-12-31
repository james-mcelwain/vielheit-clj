(ns vielheit.util.error
  (:require
   [ring.util.http-response :as response]))

(defn error [e]
  [nil e])

(defn ok [result]
  [result nil])

(defn unwrap* [[result error]]
  (if (some? error)
    error
    result))
