(ns vielheit.ajax
  (:require [ajax.core :as ajax]))

(defn local-uri? [{:keys [uri]}]
  (not (re-find #"^\w+?://" uri)))

(defn default-headers [request]
  (if (local-uri? request)
    (-> request
        (update :uri #(str js/context %))
        (update :headers #(merge {"x-csrf-token" js/csrfToken} %)))
    request))

(defn authorization [request]
  (println "auth!")
  (if-let [token (.getItem js/localStorage "token")]
    (-> request
        (update :headers #(merge {"Authorization" (str "Token " token)} %)))
    request))

(defn load-interceptors! []
    (swap! ajax/default-interceptors
           conj
           (ajax/to-interceptor {:name "default headers"
                                 :request default-headers})
           (ajax/to-interceptor {:name "autorization"
                                 :request authorization})))
