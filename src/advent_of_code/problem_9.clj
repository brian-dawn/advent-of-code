(ns advent-of-code.problem-9)

(def marker? (partial re-matches #"\((\d+)x(\d+)\)"))

(defn expand-str
  "Read an expansion string, return the new output and the remaining string we consumed."
  [xs]
  (let [potential-marker-count (->> xs
                                    (take-while (partial not= \)))
                                    count
                                    inc
                                    )
        potential-marker (->> xs (take potential-marker-count) (apply str))
        ]
    (if-let [is-marker (marker? potential-marker)]
      ;; We have to expand.
      (let [[a b] (map #(Integer/parseInt %) (rest is-marker))]
        {:expanded
         (->> xs
              (drop potential-marker-count)
              (take a)
              cycle
              (take (*
                     ;; Don't take more than the remaining original string will permit.
                     (min (- (count xs)
                             potential-marker-count)
                          a)
                     b)))
         :remaining
         (->> xs
              (drop potential-marker-count)
              (drop a))})
      ;; Just read as normal.
      {:expanded [(first xs)]
       :remaining (rest xs)})))

(defn parse [xs output]
  (if (empty? xs)
    output
    (let [head (first xs)]
      (if (= head \()
        ;; possible match case.
        (let [{:keys [expanded remaining]} (expand-str xs)]
          (parse remaining (concat output expanded)))
        ;; a regular char
        (parse (rest xs) (concat output [head]))))))

(defn solve [s] (parse s []) )

(def input (-> "day-9.txt" slurp clojure.string/split-lines))

(count (flatten (map solve input)))

;(solve "(1x2)foobar")

;(solve "(6x1)(3x1)foobar")

;(solve "X(8x2)(3x3)ABCY")

