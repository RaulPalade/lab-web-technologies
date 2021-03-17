<template>
  <div>
    <b-navbar toggleable="lg" type="dark" variant="primary" class="test">
      <b-navbar-brand href="#" class="title">Lesson Scheduling</b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item>
            <router-link to="/" class="navbar-item">Home</router-link>
          </b-nav-item>
          <b-nav-item>
            <router-link to="/about" class="navbar-item">About</router-link>
          </b-nav-item>
          <b-nav-item>
            <router-link to="/bookings" class="navbar-item">
              Bookings
            </router-link>
          </b-nav-item>
        </b-navbar-nav>
      </b-collapse>

      <span v-if="isLoggedIn">
        <b-button variant="light"><a @click="logout">Logout</a></b-button>
      </span>
      <span v-else>
        <router-link to="/login">
          <b-button variant="light">Login</b-button>
        </router-link>
      </span>
    </b-navbar>
  </div>
</template>

<script>
export default {
  name: "Nav",
  computed: {
    isLoggedIn: function () {
      return this.$store.getters.isAuthenticated;
    },
  },
  methods: {
    async logout() {
      await this.$store.dispatch("LogOut");
      this.$router.push("/login");
    },
  },
};
</script>

<style>
.navbar a {
  color: #fff;
  font-size: 1.5rem;
}
</style>