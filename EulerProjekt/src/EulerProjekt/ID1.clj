(defn id1 [x] 
  (reduce + (filter #(or (= 0 (mod % 3)) 
                         (= 0 (mod % 5))) 
                    (take x (iterate inc 1))))
)

;=> (id1 999)
;233168