(ns advent-of-code.problem-7)

(defn abba? [xs]
  (let [[a b c d] xs]
    (and (= a d)
         (= b c)
         (not= a b)
         (= 4 (count xs)))))

(def input (slurp "day-7.txt"))

(def data (clojure.string/split-lines input))



(count
 (filter
  (fn [ip]
    (let [clean (partial filter (comp abba? first))
          inside-brackets  (->> ip
                                (re-seq #"\[.*?]")
                                (map rest)
                                (map butlast)
                                (map (partial apply str))
                                )
          
          outside-brackets (clojure.string/split ip #"\[.*?\]")] 

      (and
       (->> outside-brackets
            (map (partial partition 4 1))
            (apply concat)
            (some abba?)) 
       
       (->> inside-brackets
            (map (partial partition 4 1))
            (apply concat)
            (not-any? abba?)))))

  data))





;; part 2

(defn aba? [[a b c :as xs]]
  (and
   (= a c)
   (not= a b)
   (= 3 (count xs))))



(count
 (filter
  (fn [ip]
    (let [clean (partial filter (comp abba? first))
          inside-brackets  (->> ip
                                (re-seq #"\[.*?]")
                                (map rest)
                                (map butlast)
                                (map (partial apply str))
                                )
          
          outside-brackets (clojure.string/split ip #"\[.*?\]")] 

      (and
       (->> outside-brackets
            (map (partial partition 4 1))
            (apply concat)
            (some abba?)) 
       
       (->> inside-brackets
            (map (partial partition 4 1))
            (apply concat)
            (not-any? abba?)))))

  data))
