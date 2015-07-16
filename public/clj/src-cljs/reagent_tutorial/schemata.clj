(ns reagent-tutorial.schemata
  (:require [schema.core :as s]
            [clojure.test :as test]))

(def Message
  {:content s/Str})

(def User
  {:email s/Str})

(def Alert
  {:message Message
   :alert-type (s/enum "One" "Two" "Three")
   :id s/Int
   :recipient User})

;;-----------------------------------------------------------------------------
;; Examples:

;; This is ok:
(s/validate Alert {:recipient {:email "hello@example.com"}
                   :id 123
                   :alert-type "One"
                   :message {:content "Hello!"}})

;; This throws an exception since we're missing the :recipient key.
(is (thrown? clojure.lang.ExceptionInfo
             (s/validate Alert {:id 123
                                :alert-type "One"
                                :message {:content "Hello!"}})))

;; This throws an error since the :id is a string.
(is (thrown? clojure.lang.ExceptionInfo
             (s/validate Alert {:recipient {:email "hello@example.com"}
                                :id "123"
                                :alert-type "One"
                                :message {:content "Hello!"}})))

;; This throws an error since the :alert-type is not in the :alert-type enum.
(is (thrown? clojure.lang.ExceptionInfo
             (s/validate Alert {:recipient {:email "hello@example.com"}
                                :id "123"
                                :alert-type "No no no!"
                                :message {:content "Hello!"}})))
