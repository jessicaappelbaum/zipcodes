(ns zipcodes.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [schema.core :as s]
            [ring.swagger.schema :refer [coerce!]]
            [clojure.data.json :as json]))



(def zips-data (json/read-str (slurp "resources/zips.json")
                              :key-fn keyword))


(s/defschema ZipCode {:city String
                      :loc Double
                      :pop Long
                      :state String
                      :_id String
                      })

(defonce zip* (atom (take 4 zips-data)))

#_(defn get-all-info [] (-> ))

#_(defn get-info [_id] (@zip* _id))

(defapi app
  {:swagger
   {:info {:title "ZIP API"
           :description "an exercize in getting started"}
    :tags [{:name "api", :description "this is a cool get thing that gets info about a zipcode, hopefully"}]}}

  (context "/api" []
           :tags ["api"]
           
           (GET "/" []
                :return [ZipCode]
                :summary "gets all zipcodes"
                (ok (get-info id)))))

#_(def parsed-json
    (coerce/coercer two coerce/json-coercion-matcher))

;;(def two (take 2 zips-data))

#_(defapi app
  {:swagger
   {:ui "/"
    :spec "/swagger.json"
    :data parsed-json}}

  (context "/api" []
           :tags ["api"]

           (GET "/zip-info" []
                :return (take 2 parsed-json))))

;; (s/defschema Pizza
;;   {:name s/Str
;;    (s/optional-key :description) s/Str
;;    :size (s/enum :L :M :S)
;;    :origin {:country (s/enum :FI :PO)
;;             :city s/Str}})

;; (def app
;;   (api
;;     {:swagger
;;      {:ui "/"
;;       :spec "/swagger.json"
;;       :data {:info {:title "Zipcodes"
;;                     :description "Compojure Api example"}
;;              :tags [{:name "api", :description "some apis"}]}}}

;;     (context "/api" []
;;       :tags ["api"]

;;       (GET "/plus" []
;;         :return {:result Long}
;;         :query-params [x :- Long, y :- Long]
;;         :summary "adds two numbers together"
;;         (ok {:result (+ x y)}))

;;       (POST "/echo" []
;;         :return Pizza
;;         :body [pizza Pizza]
;;         :summary "echoes a Pizza"
;;         (ok pizza)))))
