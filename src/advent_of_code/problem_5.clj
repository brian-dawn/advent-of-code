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

(println "part 1:"
         (apply str (-> (fn [acc i]
                          (if (= 8 (count acc))
                            (reduced acc) ;; stop
                            (let [start (->> (md5 (str input i))
                                             (take 6))]

                              (if (= (butlast start) (repeat 5 \0))
                                (conj acc (last start))
                                acc))))
                        (reduce [] (range)))))


(println "part 2:"
         (apply str
                (reduce
                 (fn [password i]
                   (if (not-any? nil? password)
                     (reduced password)
                     (let [start (->> (md5 (str input i))
                                      (take 7))]
                       (if (= (take 5 start) (repeat 5 \0))
                         (let [position (-> (nth start 5) int (- 48))
                               character (nth start 6)]
                           (if (and (< -1 position (count password))
                                    (nil? (get password position))) 
                             (assoc password position character)
                             password))
                         password))))
                 (vec (take 8 (repeat nil)))
                 (range))))
