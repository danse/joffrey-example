(ns reagent-tutorial.core
  (:require [clojure.string :as string]
            [reagent.core :as r]))

(enable-console-print!)

;; The "database" of your client side UI.
(def auth
  (r/atom
    {:name "joffrey-admin@example.com"
     :password "adminadmin"
     :logged-in false }))

(def app-state
  (r/atom
    {}))

;; Util stuff

;; Include token when we have it
(.ajaxSetup js/$ (clj->js {:beforeSend (fn [xhr]
                                         (when-let [token (:bearerToken @auth)]
                                           (.setRequestHeader xhr "authorization" (str "Bearer " token))))}))

(defn stringify [obj]
  (.stringify js/JSON (clj->js obj)))

;; Login, Logout
(defn login [e]
  (.preventDefault e)
  (.ajax js/$ (clj->js {:type "POST"
                        :url "/joffrey/accounts/session"
                        :contentType "application/json"
                        :data (-> @auth (select-keys [:name :password]) stringify)
                        :success (fn [resp]
                                   (let [resp (js->clj resp)]
                                     (.log js/console resp)
                                     (swap! auth assoc :logged-in true)
                                     (swap! auth dissoc :msg)
                                     (swap! auth merge resp)))
                        :error (fn [xhr status resp]
                                   (swap! auth assoc :msg resp :logged-in false))})))

(defn logout [e]
  (.preventDefault e)
  (.ajax js/$ (clj->js {:type "DELETE"
                        :url "/joffrey/accounts/session"
                        :success (fn [resp]
                                   (let [resp (js->clj resp)]
                                     (reset! auth {:logged-in false})))
                        :error (fn [xhr status resp]
                                   (.alert js/window resp))})))

;; UI components
(defn login-form []
  [:form {:name "login-form"
          :action "/joffrey/accounts/session"
          :class "form-inline"
          :on-submit login}
   (when-let [msg (:msg @auth)]
     [:div  msg])
   [:div {:class "form-group"}
    [:label {:for "email"} "Email"]
    [:input {:type "text"
              :class "form-control"
              :name "email"
              :value (-> @auth :name)
              :on-change #(swap! auth assoc :name (-> % .-target .-value))
              :placeholder "john@example.com" }]]
   [:div {:class "form-group"}
    [:label {:for "password"} "Password"]
    [:input {:type "password"
              :class "form-control"
              :name "password"
              :value (-> @auth :password)
              :on-change #(swap! auth assoc :password (-> % .-target .-value))
              :placeholder "secret"}]]
   [:button {:type "submit" :class "btn btn-primary"} "Sign In"]])

(defn logout-form []
  [:p (str "Welcome back, " (get @auth "fullname"))
   [:br]
   [:a {:id "signout" :href "signOut" :on-click logout} "Sign Out"]])

(defn app []
  (let [logged-in (-> @auth :logged-in)]
    [:div {:id "account"} (if logged-in
                              (logout-form)
                              (login-form))] ))

;; Render the root component
(defn start []
  (r/render-component
   [app]
   (.getElementById js/document "root")))
