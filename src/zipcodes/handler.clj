(ns zipcodes.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [schema.core :as s]
            [clojure.data.json :as json]
            [zipcodes.middleware :refer :all]
            [ring.middleware.params :refer :all]))



(def zips-data (json/read-str
                 (slurp "resources/zips.json")
                              :key-fn keyword))


(s/defschema ZipCode {:city String
                      :loc [Double]
                      :pop Long
                      :state String
                      :_id String
                      })

(defonce data* (atom (take 20 zips-data)))


(defn get-info [_id]
  (->> zips-data
       (filter (fn [z] (if (= _id (:_id z)) true false)))
       (first)))

(defapi app
  {:swagger
   {:ui "/"
    :spec "/swagger.json"
    :info {:title "ZIP API"
           :description "an exercize in getting started"}
    :tags [{:name "api", :description "this is a  thing that gets a vareity of info about a zipcode"}]}}

  (context "/zipcode" []
           :tags ["zipcode"]
           
           (GET "/all" []
                :return [ZipCode]
                :query-params [{offset :- Long 0}
                               {limit :- Long 1}]
                :middleware [wrap-paginate]
                :summary "gets all zipcodes, in a paginated fashion"
                (ok @data*))

           (GET "/:id" []
                :return ZipCode
                :path-params [id :- String]
                :summary "retrieves info on a zipcode given a zipcode"
                :middleware [wrap-request-time]
                (ok (get-info id)))))

