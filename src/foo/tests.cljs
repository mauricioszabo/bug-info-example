(ns foo.tests
  (:require [clojure.test :refer-macros [testing async is]]
            [devcards.core :as cards :include-macros true]
            [matcher-combinators.test]))

(defn some-code [a]
  (js/Promise. (fn [resolve]
                 (js/setTimeout #(resolve (+ a 10))
                                1000))))

(set! cards/test-timeout 8000)
(cards/deftest some-async-test
  (async done
    (.. (some-code 12)
        (then (fn [res] (is (match? 21 res))))
        (then done)
        (catch done))))

(cards/deftest correct-async-test
  (async done
    (.. (some-code 12)
        (then (fn [res] (is (= 21 res))))
        (then done)
        (catch done))))

(defn main [])
(cards/start-devcard-ui!)
