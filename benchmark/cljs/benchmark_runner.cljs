(ns cljs.benchmark-runner
  (:refer-clojure :exclude [println])
  (:require [cljs.reader :as reader]))

(def println print)

(set! *print-fn* js/print)

(println ";;; satisfies?")
(simple-benchmark [coll (list 1 2 3)] (satisfies? ISeq coll) 1000000)
(simple-benchmark [coll [1 2 3]] (satisfies? ISeq coll) 1000000)
(println)

(println ";;; list ops")
(simple-benchmark [coll (list 1 2 3)] (first coll) 1000000)
(simple-benchmark [coll (list 1 2 3)] (-first coll) 1000000)
(simple-benchmark [coll (list 1 2 3)] (rest coll) 1000000)
(simple-benchmark [coll (list 1 2 3)] (next coll) 1000000)
(println)

(println ";;; vector ops")
(simple-benchmark [coll [1 2 3]] (first coll) 1000000)
(simple-benchmark [coll (seq [1 2 3])] (first coll) 1000000)
(simple-benchmark [coll [1 2 3]] (conj coll 4) 1000000)
(simple-benchmark [coll [1 2 3]] (-conj coll 4) 1000000)
(simple-benchmark [coll [1 2 3]] (nth coll 1) 1000000)
(simple-benchmark [coll [1 2 3]] (-nth coll 1) 1000000)
(simple-benchmark [coll [1 2 3]] (rest coll) 1000000)
(simple-benchmark [coll [1 2 3]] (next coll) 1000000)
(println)

(println ";;; map / record ops")
(simple-benchmark [coll {:foo 1 :bar 2}] (get coll :foo) 1000000)
(simple-benchmark [coll {:foo 1 :bar 2}] (-lookup coll :foo nil) 1000000)
(simple-benchmark [coll {:foo 1 :bar 2}] (:foo coll) 1000000)
(defrecord Foo [bar baz])
(simple-benchmark [coll (Foo. 1 2)] (:bar coll) 1000000)
(simple-benchmark [coll {:foo 1 :bar 2}] (assoc coll :baz 3) 100000)
(simple-benchmark [coll {:foo 1 :bar 2}] (assoc coll :foo 2) 100000)
(println)

(println ";;; seq ops")
(simple-benchmark [coll (range 500000)] (reduce + coll) 1)
(println)

(println ";;; reader")
(simple-benchmark [s "{:foo [1 2 3]}"] (reader/read-string s) 1000)
(println)

(println "\n")