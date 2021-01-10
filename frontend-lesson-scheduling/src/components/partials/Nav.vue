<template>
  <div>
    <b-navbar toggleable="lg" type="dark" variant="info" class="test">
      <b-navbar-brand href="#">Lesson Scheduling</b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item>
            <router-link to="/" class="navbar-item">Home</router-link>
          </b-nav-item>
          <b-nav-item>
            <router-link to="/about" class="navbar-item">About</router-link>
          </b-nav-item>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
export default {
  name: "Nav",
};
</script>

<style>
</style>