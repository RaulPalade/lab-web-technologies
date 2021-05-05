<template>
  <div>
    <b-navbar toggleable="lg" class="navbar">
      <b-navbar-brand href="/">
        <img class="navbar-logo" src="../../assets/logoResized.svg" alt="Logo"
      /></b-navbar-brand>
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
      <b-collapse id="nav-collapse" class="justify-content-end" is-nav>
        <b-navbar-nav>
          <b-nav-item>
            <span v-if="isLoggedIn && isAdmin">
              <router-link
                to="/adminDashboard"
                class="navbar-item nav-link nav-link-ltr"
                >Dashboard</router-link
              >
            </span>
            <span v-else>
              <router-link
                to="/UserDashboard"
                class="navbar-item nav-link nav-link-ltr"
                >Dashboard</router-link
              >
            </span>
          </b-nav-item>

          <b-nav-item>
            <router-link to="/about" class="navbar-item nav-link nav-link-ltr"
              >About</router-link
            >
          </b-nav-item>
        </b-navbar-nav>
      </b-collapse>
      <span v-if="isLoggedIn">
        <b-button pill id="btn-logout"><a @click="logout">Logout</a></b-button>
      </span>
      <span v-else>
        <router-link to="/login">
          <b-button pill id="btn-login">Login</b-button>
        </router-link>
      </span>
    </b-navbar>
  </div>
</template>

<script>
export default {
  name: "Navbar",
  computed: {
    isLoggedIn: function() {
      return this.$store.getters.isAuthenticated;
    },
    isAdmin: function() {
      return this.$store.getters.isAdmin;
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
.nav-link {
  text-decoration: none;
  text-transform: capitalize;
  position: relative;
}

.nav-link:hover {
  opacity: 1;
}

.nav-link::before {
  transition: 300ms;
  height: 5px;
  content: "";
  position: absolute;
  background-color: #017d91;
}

.nav-link-ltr::before {
  width: 0%;
  bottom: 10px;
}

.nav-link-ltr:hover::before {
  width: 100%;
}

#btn-login {
  font-weight: 900;
  font-size: 1.3rem;
  text-transform: capitalize;
  border-style: none;
  background: #017d91;
  transition: 0.5s all ease-in-out;
}

#btn-login:hover {
  background: #60d69a;
}

#btn-logout {
  font-weight: 900;
  font-size: 1.3rem;
  text-transform: capitalize;
  border-style: none;
  background: #60d69a;
  transition: 0.5s all ease-in-out;
}

#btn-logout:hover {
  background: #017d91;
}
</style>
