(defproject pizza-slices "0.1.0-SNAPSHOT"
  :description "Pizza slices first attempt!"
  :license {:name "MIT License"
            :url "http://www.opensource.org/licenses/mit-license.php"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot pizza-slices.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
