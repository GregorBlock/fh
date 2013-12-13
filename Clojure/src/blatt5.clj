(ns blatt5.core)
(use '[clojure.contrib.lazy-seqs :only (primes)])

;(defn prime? [x] (=( count(filter #(=(rem x %) 0) (range 1 x))) 1))

;(defn is-prime? [n] (zero? (count (filter #(zero? (rem n %)) (range 3 n 2)))))



(defn integers [start]
  (cons start (lazy-seq (integers (inc start))))
  )

(defn prime? [n]
  (= (count (filter #(zero? (mod n %)) (take-while #(< % (/ n 2)) (integers 1)))) 1)
  )


; Aufgabe 1.
(defn goldbach-partition [n]
  (first (map #(list (- n %) %) (filter prime? (map #(- n %) (take-while #(<= % (/ n 2)) primes)))))
  )

; Aufgabe 2.
(defn goldbach-partitions [n]
  (map #(list (- n %) % ) (filter prime? (map #(- n %) (take-while #(<= % (/ n 2)) primes))))
  )


;Aufgabe 3
(defn next-collatz [n]
  (cond (= 1 n) 1
    (even? n) (quot n 2)
    :default (inc (* 3 n))))

(defn collatz-seq [n]
  (concat (take-while #(> % 1) (iterate next-collatz n)) '(1)))

(defn collatz-seq-len [n]
  (count (collatz-seq n)))