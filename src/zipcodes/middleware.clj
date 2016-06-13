(ns zipcodes.middleware)


;;this middleware function logs the time a request takes 
(defn wrap-request-time [handler]
  (fn [request]
    (let [current-time (System/currentTimeMillis)
          response (handler request)
          end-time (System/currentTimeMillis)]
      (println (- end-time current-time))
      response)))

;;this middleware function will paginate

(defn wrap-paginate [handler]
  (fn [request]
    (let [query-params (:params request)
          per-page (read-string (:limit query-params))
          response (handler request)
          limited-results (take per-page (:body response))]

      (println (take per-page (:body response)))
      (assoc response :body limited-results)

      )))


;; (defn wrap-paginate [query-paramas page per-page]
;;     (-> query-params
;;         (limit per-page)
;;         (offset (dec page))))
