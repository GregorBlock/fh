(ns ID3.core)
(use '[clojure.contrib.lazy-seqs :only (primes)])


;The prime factors of 13195 are 5, 7, 13 and 29.

;What is the largest prime factor of the number 600851475143 ?

(defn lagergestPrimeFactorOf [x]
  ((map #(/ x %) (take x primes))
)