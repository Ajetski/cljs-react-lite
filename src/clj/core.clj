(ns core
  (:require
   [ring.adapter.jetty :refer [run-jetty]]
   [ring.util.response :refer [redirect]]
   [compojure.core :refer [defroutes GET]]
   [compojure.route :as route]))

; for development, eval file to restart server
(when (resolve 'server)
  (declare server)
  (.stop server))

(defn get-cwd []
  (-> (java.io.File. ".") .getAbsolutePath))

(defroutes app
  (GET "/" [] (redirect "/assets/index.html"))
  (route/resources "/assets")
  (route/files "/assets/cljs" {:root (str (get-cwd) "/src/cljs/")})
  (route/files "/assets/cljc" {:root (str (get-cwd) "/src/cljc/")})
  (route/not-found "<h1>Page not found</h1>"))

(defn -main
  [& args]
  (run-jetty app {:port 3000
                  :join? false}))

(def server (-main))

