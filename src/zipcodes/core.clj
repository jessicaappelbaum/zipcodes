(ns zipcodes.core)

(defn wrap-middleware [handler]
  (fn [request]
    (let [current-time (System/currentTimeMillis)
          response (handler request)
          end-time (System/currentTimeMillis)]
      (println (- end-time current-time))
      response)))
