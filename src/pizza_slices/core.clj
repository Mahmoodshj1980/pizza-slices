(ns pizza-slices.core
  (require [clojure.string :as str])
  (:gen-class))

(defn- get-coordinates
  "Utility function to iterate over the pizza matrix"
  [rows columns]
  (for [x (range 0 rows)
        y (range 0 columns)]
    [x y]))

(defn- ingredients-count
  "Generates a map of ingredients and counts"
  [slice]
  (reduce (partial merge-with +) (map frequencies slice)))

(defn- check-slice
  "Checks if the slice is valid"
  [slice min-ingredients]
  (let [ingredients (ingredients-count slice)]
    (not (or (contains? ingredients "X")
             (< (get ingredients "M" 0) min-ingredients)
             (< (get ingredients "T" 0) min-ingredients)))))

(defn- get-slice
  "Search for a valid pizza slice from specified coordinates"
  [min-ingr max-size coord pizza]
  nil) ; TODO: currently we can't find valid slices...

(defn- cut-slice
  "Cuts the slice in the pizza and returns the new pizza status"
  [pizza slice]
  pizza) ; TODO: currently we don't update anything!

(defn- update-status
  "Updates slices and pizza status based on a new fresh slice"
  [current-status slice]
  (if (nil? slice)
    current-status
    (update current-status :pizza (cut-slice pizza slice)
                           :slices (concat (:slices current-status) slice))))

(defn- process-coordinate
  "Starting at some coordinate tries to find a valid slice and cut it from the pizza"
  [min-ingr max-size current-status coord]
  (->> current-status
       (get-slice min-ingr max-size coord (:pizza current-status))
       (update-status current-status))

(defn- parse-file
  "Reads input file into a vector"
  [filename]
  (with-open [rdr (clojure.java.io/reader filename)]
    (->> (line-seq rdr)
         (map #(re-seq #"\S" %))
         (into []))))

(defn- process-file
  "Resolves the problem of the pizza!"
  [parsed-file]
  (let [[config & data] parsed-file
        [rows cols min-ingr max-size] (map read-string config)
        coords (get-coordinates rows cols)
        init-status {:slices [] :pizza data}]
    (:slices (reduce #(process-coordinate min-ingr max-size %1 %2)
                     {:slices [] :pizza data}
                      data))))

(defn- write-results
  "Write results to the file results.out"
  [results]
  (with-open [wrtr (clojure.java.io/writer "results.out")]
    (.write wrtr (str (count results)))
    (.newLine wrtr)
    (doseq [slice results]
      (.write wrtr (str/join " " slice))
      (.newLine wrtr))))

(defn -main
  "Entry point to the app"
  [& args]
  (if-let [filename (first args)]
    (-> filename
        parse-file
        process-file
        write-results)))
