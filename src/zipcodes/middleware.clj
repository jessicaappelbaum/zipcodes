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

;; (defn wrap-paginate [handler]
;;   (fn [request]
;;     (let [response (handler request)
;;           offset (System/)]
;;       (println offset)
;;       response)))

;; (defn wrap-paginate [query-paramas page per-page]
;;     (-> query-params
;;         (limit per-page)
;;         (offset (dec page))))
