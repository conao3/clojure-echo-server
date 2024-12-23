(ns echo-server.core
  (:require
   [clojure.tools.logging :as log]
   [clojure.java.io :as io])
  (:import
   [java.net ServerSocket]
   [java.io BufferedReader BufferedWriter])
  (:gen-class))

(defn handle-client [client-socket]
  (let [input-stream ^BufferedReader (io/reader client-socket)
        output-stream ^BufferedWriter (io/writer client-socket)]
    (loop []
      (let [inpt (. input-stream readLine)]
        (println "Received: " inpt)
        (when (and inpt (not= "" inpt))
          (. output-stream write (str inpt "\n"))
          (. output-stream flush))
        (when-not (nil? inpt)         ; nil indicates EOF
          (recur))))))

(defn start-server []
  (println "Listening localhost:15154")
  (let [server-socket (ServerSocket. 15155)]
    (loop []
      (let [client-socket (. server-socket accept)]
        (println "Connection Accepted")
        (handle-client client-socket))
      (recur))))

(defn -main
  "The entrypoint."
  [& args]
  (log/info args))
