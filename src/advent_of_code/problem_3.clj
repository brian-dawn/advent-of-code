(ns advent-of-code.problem-3)

(def input (slurp "day-3.txt"))

(def triangles (->> input
                    (re-seq #"(\d+)\s+(\d+)\s+(\d+)")
                    (map (fn [match-data]
                           (map #(Integer/parseInt %) (rest match-data))))))
(println "part 1"
       (count (filter
               (fn [[a b c]]
                 (and
                  (< a (+ b c))
                  (< b (+ a c))
                  (< c (+ a b))))
               triangles)))

(println "part 2"
         (->> 
          (for [i (range 3)]
            (map #(nth % i) triangles))
          flatten
          (partition 3)
          (filter (fn [[a b c]]
                    (and
                     (< a (+ b c))
                     (< b (+ a c))
                     (< c (+ a b)))))
          count))




