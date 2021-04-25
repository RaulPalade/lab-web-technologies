import Vue from "vue";
import VueRouter from "vue-router";
import store from "../store";
import Home from "../views/Home.vue";
import AdminDashboard from "../views/AdminDashboard.vue";
import About from "../views/About.vue"
import Login from "../views/Login";

Vue.use(VueRouter);

const routes = [{
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/adminDashboard",
    name: "AdminDashboard",
    component: AdminDashboard,
    async beforeEnter(to, from, next) {
      if (store.getters.isAuthenticated && store.getters.isAdmin) {
        next();
      } else {
        next({
          name: "Home"
        })
      }
    }
  },
  {
    path: "/about",
    name: "About",
    component: About,
    meta: {
      guest: true
    },
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
    meta: {
      guest: true
    },
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

/**
 * Ferma gli utenti non autenticati che cercano di accedere 
 * a routes che richiedono il meta {requiresAuth: true}
 * Se l'utente è già autenticato può proseguire liberamente
 * Se l'utente non è autenticato viene rimandato a /login
 */
// router.beforeEach((to, from, next) => {
//   if (to.matched.some((record) => record.meta.requiresAuth)) {
//     if (store.getters.isAuthenticated) {
//       next();
//       return;
//     }
//     next("/login");
//   } else {
//     next();
//   }
// });

/**
 * Ferma gli utenti autenticati che cercano di accedere a routes 
 * che richiedono il meta {guest: true}
 * Se l'utente è già autenticato non può accedere a meta {guest: true}
 * Se l'utente non è autenticato può accedere a meta {guest: true}
 */
// router.beforeEach((to, from, next) => {
//   if (to.matched.some((record) => record.meta.guest)) {
//     if (store.getters.isAuthenticated) {
//       next("/bookings");
//       return;
//     }
//     next();
//   } else {
//     next();
//   }
// });

export default router;