(defn integers [start]
  (cons start (lazy-seq (integers (inc start))))
  )
(defn divsum [n]
  (reduce + (filter #(= (rem n %) 0) (range 1 n))w)
  ) 

 (defn amicable5? [a b]
  (and (= a (divsum b)) (= b (divsum a) ))
  )
 
(defn friend? [a]
  (let [candidate (divsum a)]
    (if (and (amicable5? a candidate) (> a candidate))
      (list candidate a)
    )
  )
)

(defn amicables [n]
  (take n
    (filter #(not (nil? %))
      (map friend? (integers 1))
    )
  )
)



