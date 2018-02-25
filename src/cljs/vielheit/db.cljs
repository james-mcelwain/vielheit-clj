(ns vielheit.db)

(def default-db
  {:page :home
   :logged-in false
   :user nil

   ;; PAGES
   :page-login/error "error!"
   :page-login/submitting false
   :page-login/email ""
   :page-login/password ""
   })
