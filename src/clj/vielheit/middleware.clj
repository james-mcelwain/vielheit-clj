(ns vielheit.middleware
  (:require [buddy.auth :refer [authenticated?]]
            [buddy.auth.accessrules :refer [restrict]]
            [buddy.auth.backends.token :refer [jwe-backend]]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
            [buddy.core.keys :as keys]
            [buddy.core.nonce :refer [random-bytes]]
            [buddy.sign.jwt :refer [encrypt]]
            [cheshire.generate :as cheshire]
            [clj-time.core :refer [plus now minutes]]
            [clojure.tools.logging :as log]
            [clojure.java.io :as io]
            [cognitect.transit :as transit]
            [immutant.web.middleware :refer [wrap-session]]
            [muuntaja.core :as muuntaja]
            [muuntaja.format.json :refer [json-format]]
            [muuntaja.format.transit :as transit-format]
            [muuntaja.middleware :refer [wrap-format wrap-params]]
            [ring.middleware.anti-forgery :refer [wrap-anti-forgery]]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [ring.middleware.flash :refer [wrap-flash]]
            [ring.middleware.webjars :refer [wrap-webjars]]
            [vielheit.config :refer [env]]
            [vielheit.layout :refer [*app-context* error-page]]
            [vielheit.env :refer [defaults]])
  (:import [javax.servlet ServletContext]
           [org.joda.time ReadableInstant]))

(def secret "Password1")
(def pub-key (keys/public-key (io/resource "keys/pubkey.pem")))
(def priv-key (keys/private-key (io/resource "keys/privkey.pem") secret))

(defn wrap-context [handler]
  (fn [request]
    (binding [*app-context*
              (if-let [context (:servlet-context request)]
                ;; If we're not inside a servlet environment
                ;; (for example when using mock requests), then
                ;; .getContextPath might not exist
                (try (.getContextPath ^ServletContext context)
                     (catch IllegalArgumentException _ context))
                ;; if the context is not specified in the request
                ;; we check if one has been specified in the environment
                ;; instead
                (:app-context env))]
      (handler request))))

(defn wrap-internal-error [handler]
  (fn [req]
    (try
      (handler req)
      (catch Throwable t
        (log/error t (.getMessage t))
        (error-page {:status 500
                     :title "Internal Server Error"
                     :message ""})))))

(defn wrap-uuid [handler]
  (fn [req]
    (assoc req :uuid (java.util.UUID/randomUUID))
    (handler req)))

(defn wrap-csrf [handler]
  (wrap-anti-forgery
    handler
    {:error-response
     (error-page
       {:status 403
        :title "Invalid anti-forgery token"})}))

(def joda-time-writer
  (transit/write-handler
    (constantly "m")
    (fn [v] (-> ^ReadableInstant v .getMillis))
    (fn [v] (-> ^ReadableInstant v .getMillis .toString))))

(cheshire/add-encoder
  org.joda.time.DateTime
  (fn [c jsonGenerator]
    (.writeString jsonGenerator (-> ^ReadableInstant c .getMillis .toString))))

(def restful-format-options
  (update
    muuntaja/default-options
    :formats
    merge
    {"application/json"
     json-format

     "application/transit+json"
     {:decoder [(partial transit-format/make-transit-decoder :json)]
      :encoder [#(transit-format/make-transit-encoder
                   :json
                   (merge
                     %
                     {:handlers {org.joda.time.DateTime joda-time-writer}}))]}}))

(defn wrap-formats [handler]
  (let [wrapped (-> handler wrap-params (wrap-format restful-format-options))]
    (fn [request]
      ;; disable wrap-formats for websockets
      ;; since they're not compatible with this middleware
      ((if (:websocket? request) handler wrapped) request))))

(defn on-error [request response]
  (error-page
   {:status 403
    :title (str "Access to " (:uri request) " is not authorized")}))

(defn wrap-restricted [handler]
  (restrict handler {:handler authenticated?
                     :on-error on-error}))

(def token-backend
  (jwe-backend {:secret priv-key
                :options
                {:alg :rsa-oaep
                 :enc :a128cbc-hs256}}))

(defn token [username]
  (let [claims {:user (keyword username)
                :exp (plus (now) (minutes 60))}]
    (encrypt claims pub-key {:alg :rsa-oaep
                             :enc :a128cbc-hs256})))

(defn wrap-auth [handler]
  (let [backend token-backend]
    (-> handler
        (wrap-authentication backend)
        (wrap-authorization backend))))

(defn wrap-base [handler]
  (-> ((:middleware defaults) handler)
      wrap-uuid
      wrap-auth
      wrap-webjars
      wrap-flash
      (wrap-session {:cookie-attrs {:http-only true}})
      (wrap-defaults
        (-> site-defaults
            (assoc-in [:security :anti-forgery] false)
            (dissoc :session)))
      wrap-context
      wrap-internal-error))
