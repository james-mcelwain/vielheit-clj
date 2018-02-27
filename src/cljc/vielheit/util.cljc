(ns vielheit.util)

(defn ?assoc
  "Same as assoc, but skip the assoc if v is nil"
  [m & kvs]
  (reduce
   #(let [[k v] %2]
      (if (not (nil? v))
        (assoc %1 k v)
        %1))
   m (partition 2 kvs)))
