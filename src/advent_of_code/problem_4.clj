(ns advent-of-code.problem-4)

(def input (clojure.string/split-lines (slurp "day-4.txt")))

(def data
  (->> input
       (map #(clojure.string/split % #"-"))
       (map #(let [[_ sector-id chksum] (re-find #"(\d+)\[(\w+)\]" (last %))]
               {:data (butlast %)
                :sector-id (Integer/parseInt sector-id)
                :chksum chksum}))))

(->
 (fn [acc {:keys [chksum data sector-id]}]

   (let [calculated-chksum
         (->> data
              (apply concat)
              frequencies
              (sort-by val)
              reverse
              ;; Sort same count alphabetically
              (partition-by second)
              (map (partial sort-by first))
              ;; Get just the keys.
              (map (partial keys))
              flatten
              (apply str))]
     
     (if (.startsWith calculated-chksum chksum)
       (+ acc sector-id)
       acc)))
 
 (reduce 0 data))



