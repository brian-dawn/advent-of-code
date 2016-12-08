(ns advent-of-code.problem-5)

;; Found the below floating on the interwebs: https://gist.github.com/jizhang/4325757
(defn md5 [s]
  (let [algorithm (java.security.MessageDigest/getInstance "MD5")
        size (* 2 (.getDigestLength algorithm))
        raw (.digest algorithm (.getBytes s))
        sig (.toString (java.math.BigInteger. 1 raw) 16)
        padding (apply str (repeat (- size (count sig)) "0"))]
    (str padding sig)))


(def input "cxdnnyjw")

(apply str (-> (fn [acc i]
                 (if (= 8 (count acc))
                   (reduced acc) ;; stop
                   (let [start (->> (md5 (str input i))
                                    (take 6))]

                     (if (= (butlast start) (repeat 5 \0))
                       (conj acc (last start))
                       acc))))
               (reduce [] (range))))





