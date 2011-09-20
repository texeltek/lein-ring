(ns leiningen.ring.server-uberwar
  (:import (org.mortbay.jetty Server)
           (org.mortbay.jetty.webapp WebAppContext))
  (:require [leiningen.ring.uberwar :as uberwar]))

(def suitable-ports (range 3000 3011))

(defn- jetty-run-war [war]
  (let [context (WebAppContext. war "/")]
    (doto (Server. (first suitable-ports))
      (.setHandler context)
      (.start)
      (.join))))

(defn server-uberwar
  "Create a $PROJECT-$VERSION.war with dependencies and run the war in jetty container."
  ([project]
   (server-uberwar project (uberwar/default-uberwar-name project)))
  ([project war]
   (uberwar/uberwar project war)
   (jetty-run-war war)))
