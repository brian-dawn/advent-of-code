(ns advent-of-code.problem-8)



(def input (slurp "day-8.txt"))


(def data (->> input
               clojure.string/split-lines
               (map #(clojure.string/split % #" "))))







(defn make-screen [width height]
  (->> "."
       repeat
       (take width)
       vec
       repeat
       (take height)
       vec))

(defn height [screen]
  (count screen))

(defn width [screen]
  (-> screen first count))

(defn get-col [screen n]
  (map #(get % n) screen)
  )

(defn get-row [screen n]
  (get screen n)
  )

(defn update-screen [screen [x y] val]
  (or
   (and
    (< -1 y (height screen))
    (< -1 x (width screen))    
    (assoc-in screen [y x] val))
   screen))

(defn pp-screen [screen]
  (println (->> screen
                (map (partial apply str))
                (clojure.string/join "\n"))))

;(row (make-screen 4 3) 0)
;(get (make-screen 2 3) 1)
;(col (update-screen (make-screen 4 3) [0 1] \#) 0)
;(make-screen 4 6)

(defmulti manipulate
  (fn [row screen] (first row)))

(defmethod manipulate "rotate" [[_ col-or-row target _by magnitude] screen]
  (println "rotate" col-or-row target _by magnitude screen)
  (let [coord (-> target (clojure.string/split #"=") second Integer/parseInt)
        parsed-magnitude (Integer/parseInt magnitude)
        ]

    (case col-or-row
      "row" (let [row (get-row screen coord)]
              (assoc
               screen
               coord
               (->> row
                    cycle
                    (drop (- (count row) parsed-magnitude))
                    (take (count row)))))
      "column" (let [col (get-col screen coord)]
                 (println "shit we gotta update the column with..."
                  ;; also this logic is probs wrong
                  (->> col
                       cycle
                       (drop (- (count col) parsed-magnitude))
                       (take (count col))
                       
                       ))
                 (println "got the col" col)
                 )
      ))
  )

(defmethod manipulate "rect" [[_ coord] screen]
  (let [[width height] (->> (clojure.string/split coord #"x")
                            (map #(Integer/parseInt %)))]
    (reduce (fn [screen [x y]]
              (println x y)
              (update-screen screen [x y] "#"))
            screen
            (apply concat
             (for [x (range width)]
               (for [y (range height)]
                 [x y]))))))

                                        ;(update-screen (make-screen 4 4) [0 0] "#")
(->> (make-screen 7 3)
     (manipulate ["rect" "3x2"] )
;     (manipulate ["rotate" "column" "x=1" "by" "1"] )
     (manipulate ["rotate" "row" "y=0" "by" "4"])
     pp-screen
     )



(map (partial partition 1 3) )

(defn zip-col [newcol & rows]
  (map #(assoc % 0) )
  (println rows)
  )
(apply map (partial zip-col [7 7 7]) [[1 1 1] [2 2 2] [3 3 3]])










