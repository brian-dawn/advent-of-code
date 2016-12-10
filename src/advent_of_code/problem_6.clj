(ns advent-of-code.problem-6)

(def input (slurp "day-6.txt"))

(def data (clojure.string/split-lines input))

(println "part 1"
         (->> (apply map vector data)
              (map frequencies)
              (map (comp reverse (partial sort-by val)))
              (map first)
              (map first)
              (apply str)))

(println "part 2"
         (->> (apply map vector data)
              (map frequencies)
              (map (partial sort-by val))
              (map first)
              (map first)
              (apply str)))
