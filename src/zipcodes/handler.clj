(ns zipcodes.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [schema.core :as s]
            [clojure.data.json :as json]
            [zipcodes.core :refer [wrap-middleware]]))



(def zips-data (json/read-str (slurp "resources/zips.json")
                              :key-fn keyword))


(s/defschema ZipCode {:city String
                      :loc [Double]
                      :pop Long
                      :state String
                      :_id String
                      })

(defonce data* (atom zips-data))

(defn get-all-info [] (-> @data* reverse))

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
    :tags [{:name "api", :description "this is a cool get thing that gets info about a zipcode, hopefully"}]}}

  (context "/zipcode" []
           :tags ["zipcode"]
           
           (GET "/all" []
                :return [ZipCode]
                :summary "gets all zipcodes"
                (ok (get-all-info)))

           (GET "/:id" []
                :return ZipCode
                :path-params [id :- String]
                :summary "retrieves info on a zipcode given a zipcode"
                :middleware [wrap-middleware]
                (ok (get-info id)))))

