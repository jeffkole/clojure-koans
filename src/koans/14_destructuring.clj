(def test-address
  {:street-address "123 Test Lane"
   :city "Testerville"
   :state "TX"})

(meditations
  "Destructuring is an arbiter: it breaks up arguments"
  (= ":bar:foo" ((fn [[a b]] (str b a))
         [:foo :bar]))

  "Whether in function definitions"
  (= (str "First comes love, "
          "then comes marriage, "
          "then comes Clojure with the baby carriage")
     ((fn [[a b c]]
        (str "First comes " a ", then comes " b ", then comes " c " with the baby carriage"))
      ["love" "marriage" "Clojure"]))

  ;; A for comprehension results in a lazy sequence, so apply 'str' to it to
  ;; concatenate the string values together.
  "Or in let expressions"
  (= "Rich Hickey aka The Clojurer aka Go Time aka Macro Killah"
     (let [[first-name last-name & aliases]
           (list "Rich" "Hickey" "The Clojurer" "Go Time" "Macro Killah")]
       (str first-name " " last-name
           (apply str (for [a aliases] (str " aka " a))))))

  "You can regain the full argument if you like arguing"
  (= {:original-parts ["Steven" "Hawking"] :named-parts {:first "Steven" :last "Hawking"}}
     (let [[first-name last-name :as full-name] ["Steven" "Hawking"]]
       {:original-parts full-name :named-parts {:first first-name :last last-name}}))

  "Break up maps by key"
  (= "123 Test Lane, Testerville, TX"
     (let [{street-address :street-address, city :city, state :state} test-address]
       (str street-address ", " city ", " state)))

  ;; The keyword keys must match the names of the variables used
  "Or more succinctly"
  (= "123 Test Lane, Testerville, TX"
     (let [{:keys [street-address city state]} test-address]
       (str street-address ", " city ", " state)))

  ;; This one with function argument destructuring and let expression
  ;; destructuring works, but the one below does it all in the argument list.
  ;;((fn [[first-name last-name] address]
  ;;  (let [{:keys [street-address city state]} address]
  ;;    (str first-name " " last-name ", " street-address ", " city ", " state)))
  ;;  ["Test" "Testerson"] test-address)))
  "All together now!"
  (= "Test Testerson, 123 Test Lane, Testerville, TX"
     ((fn [[first-name last-name] {:keys [street-address city state]}]
        (str first-name " " last-name ", " street-address ", " city ", " state))
        ["Test" "Testerson"] test-address)))
