import Vue from "vue";
import VueRouter from "vue-router";
import store from "../store";
import Home from "../views/Home.vue";
import UserDashboard from "../views/UserDashboard.vue";
import AdminDashboard from "../views/AdminDashboard.vue";
import About from "../views/About.vue"
import Login from "../views/Login";

Vue.use(VueRouter);

const routes = [{
    path: "",
    name: "Home",
    component: Home,
    meta: {
      guest: true
    },
  },
  {
    path: "/adminDashboard",
    name: "AdminDashboard",
    component: AdminDashboard,
    meta: {
      requiresAuth: true,
      isAdmin: true
    }
  },
  {
    path: "/userDashboard",
    name: "UserDashboard",
    component: UserDashboard,
    meta: {
      requiresAuth: true,
    },
    beforeEnter(to, from, next) {
      if (store.getters.isAdmin) {
        next({
          name: "AdminDashboard"
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

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!store.getters.isAuthenticated) {
      next({
        path: '/login',
        params: {
          nextUrl: to.fullPath
        }
      })
    } else {
      if (to.matched.some(record => record.meta.isAdmin)) {
        if (store.getters.isAdmin) {
          next()
        } else {
          next({
            name: "UserDashboard"
          })
        }
      } else {
        next()
      }
    }
  } else if (to.matched.some(record => record.meta.guest)) {
    next()
  }
})

export default router;