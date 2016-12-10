(ns advent-of-code.problem-4)

(def input (clojure.string/split-lines (slurp "day-4.txt")))

(def data
  (->> input
       (map #(clojure.string/split % #"-"))
       (map #(let [[_ sector-id chksum] (re-find #"(\d+)\[(\w+)\]" (last %))]
               {:data (butlast %)
                :sector-id (Integer/parseInt sector-id)
                :chksum chksum}))))



(def alphabet (->> (range (int \a) (inc (int \z)))
                   (map char)
                   cycle))

(defn shift [amount s]
  (->> s
       (map (fn [ch]
              (->> alphabet
                   (drop-while (partial not= ch))
                   (drop amount)
                   first)))
       (apply str)))

(let [[acc decryped-messages]
      (->
       (fn [[acc decrypted] {:keys [chksum data sector-id]}]

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
             [(+ acc sector-id)
              (conj decrypted [sector-id (clojure.string/join " " (map (partial shift sector-id) data))])]
             
             [acc decrypted])))
       
       (reduce [0 #{}] data))]
  (println "part 1" acc)
  (doseq [[sector-id msg] decryped-messages]
    (when (.contains msg "north")
      (println sector-id ":" msg))))






