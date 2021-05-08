import Vue from 'vue'
import App from './App.vue'
import router from './router'
import {
  BootstrapVue,
  IconsPlugin
} from 'bootstrap-vue'
import store from './store'
import axios from 'axios'
import VueAxios from 'vue-axios'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import '@fortawesome/fontawesome-free/css/all.css'
import VueCarousel from 'vue-carousel';
import VueBootstrapToasts from "vue-bootstrap-toasts";

Vue.use(BootstrapVue)
Vue.use(IconsPlugin)
Vue.use(VueAxios, axios)
Vue.use(VueCarousel)
Vue.use(VueBootstrapToasts);

Vue.config.productionTip = false
axios.defaults.withCredentials = true

axios.interceptors.response.use(undefined, function (error) {
  if (error) {
    const originalRequest = error.config;
    if (error.response.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;
      store.dispatch('LogOut')
      return router.push('/login')
    }
  }
})

new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app')