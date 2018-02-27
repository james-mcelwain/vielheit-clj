(ns vielheit.db)

(defn default-db []
  (let [logged-in (not (nil? (.getItem js/localStorage "token")))]
    {:page :home
     :logged-in logged-in
     :user nil
     ;; PAGES
     :page-login/error nil
     :page-login/submitting false
     :page-login/email ""
     :page-login/password ""
     }))
