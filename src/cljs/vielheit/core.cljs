(ns vielheit.core
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [secretary.core :as secretary]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [markdown.core :refer [md->html]]
            [ajax.core :refer [GET POST]]
            [vielheit.ajax :refer [load-interceptors!]]
            [vielheit.events]
            [vielheit.pages.login :refer [login-page]]
            [vielheit.routes :refer [app-routes!]])
  (:import goog.History))

(defn home-page []
  [:div.container
   "home"
   ])

(def pages
  {:home #'home-page})

(defn page []
  [:div
   (if @(rf/subscribe [:logged-in])
     [(pages @(rf/subscribe [:page]))]
     [login-page])])

;; ;; -------------------------
;; ;; Initialize app
;; (defn fetch-docs! []
;;   (GET "/docs" {:handler #(rf/dispatch [:set-docs %])}))

(defn mount-components []
  (rf/clear-subscription-cache!)
  (r/render [#'page] (.getElementById js/document "app")))

(defn init! []
  (rf/dispatch-sync [:initialize-db])
  (load-interceptors!)
  (app-routes!)
  (GET "/api/user" {:handler #(println %)})
  (mount-components))
