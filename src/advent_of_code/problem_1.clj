(ns advent-of-code.problem-1)

(def input "L2, L5, L5, R5, L2, L4, R1, R1, L4, R2, R1, L1, L4, R1, L4, L4, R5, R3, R1, L1, R1, L5, 
            L1, R5, L4, R2, L5, L3, L3, R3, L3, R4, R4, L2, L5, R1, R2, L2, L1, R3, R4, L193, R3, 
            L5, R45, L1, R4, R79, L5, L5, R5, R1, L4, R3, R3, L4, R185, L5, L3, L1, R5, L2, R1, R3, 
            R2, L3, L4, L2, R2, L3, L2, L2, L3, L5, R3, R4, L5, R1, R2, L2, R4, R3, L4, L3, L1, R3, 
            R2, R1, R1, L3, R4, L5, R2, R1, R3, L3, L2, L2, R2, R1, R2, R3, L3, L3, R4, L4, R4, R4, 
            R4, L3, L1, L2, R5, R2, R2, R2, L4, L3, L4, R4, L5, L4, R2, L4, L4, R4, R1, R5, L2, L4, 
            L5, L3, L2, L4, L4, R3, L3, L4, R1, L2, R3, L2, R1, R2, R5, L4, L2, L1, L3, R2, R3, L2, 
            L1, L5, L2, L1, R4")

(def directions (map (fn [[_combined l-or-r magnitude]]
                       [l-or-r (Integer/parseInt magnitude)])
                     (re-seq #"(L|R)(\d+)" input)))

(def compass [:north :east :south :west])
(def compass-right (cycle compass))
(def compass-left (-> compass reverse cycle))

(def dir->vec {:north [0 1]
               :south [0 -1]
               :east [1 0]
               :west [-1 0]})

(defn mul-vec
  "Multiply a vector by a scalar."
  [vec scalar]
  (map (partial * scalar) vec))

(def add-vec
  "Add vectors together."
  (partial map +))

(defn steps-between
  "Get all the intermediate stops between two vectors
   assuming a straight line."
  [[ax ay] [bx by]] 
  (if (= ax bx)
    (map vector
         (repeat ax)
         (range ay by (if (< ay by) 1 -1)))
    (map vector
         (range ax bx (if (< ax bx) 1 -1))
         (repeat ay))))

(defn turn
  "Given a direction and left or right get a new direction."
  [direction l-or-r]
  (let [new-dir-fn (fn [compass]
                     (-> (partial = direction)
                         complement
                         (drop-while compass)
                         second))]
    (case l-or-r
      "L" (new-dir-fn compass-left)
      "R" (new-dir-fn compass-right))))

(def result
  (reduce
   (fn [[current-direction current-location visited bunny-hq] [l-or-r magnitude]]
     (let [new-direction (turn current-direction l-or-r)
           new-dir-vec (-> new-direction
                           dir->vec
                           (mul-vec magnitude))
           new-location (add-vec current-location new-dir-vec)
           steps-taken (steps-between current-location new-location)
           ]

       [new-direction
        new-location
        (clojure.set/union visited (set steps-taken))
        (or (not-empty bunny-hq) (reduce
                                  (fn [found step-took]
                                    (or found (visited step-took)))
                                  nil
                                  steps-taken))]))
   [:north [0 0] #{} nil]
   directions))

(defn distance [loc]
  (apply + (map #(Math/abs %) loc)))

(let [[_ final-location _ bunny-hq] result]
  (println "part 1" (distance final-location))
  (println "part 2" (distance bunny-hq)))


