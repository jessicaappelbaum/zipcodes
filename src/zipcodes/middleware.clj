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
    (let [response (handler request)
          page-number (read-string (or (:offset (:params request)) "0"))
          per-page (read-string (or (:limit (:params request)) "10"))]
      (assoc response :body (take per-page (drop page-number (:body response)))))))


;; access control middleware

(defn wrap-access-control [handler]
  (fn [request]
    (let [response (handler request)
          response (assoc-in response [:headers "Access-Control-Allow-Origin"] "*")]
      (clojure.pprint/pprint response)
      response)))


;; this is the pagination middleware from think.datomic/src/clj/think/datomic/handler.clj

;; (defn- get-param [req parameter-name]
;;   (or (get-in req [:body-params parameter-name])
;;       (get-in req [:params parameter-name])))

;; (defn wrap-pagination
;;   [handler]
;;   (fn [request]
;;     (let [response (handler request)
;;           n        (get-param request :limit)
;;           m        (get-param request :offset)]
;;       (if (and (map? (:body response))
;;                (contains? (:body response) :total-count)
;;                (contains? (:body response) :items))
;;         response
;;         (if (and (boolean n)
;;                  (boolean m)
;;                  (boolean (seq (:body response))))
;;           (assoc response
;;                  :body {:total-count  (count (:body response))
;;                         :items (take (Integer. n) (drop (Integer.  m) (:body response)))})
;;           response)))))

