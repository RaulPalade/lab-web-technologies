<template>
  <div id="app">
    <div class="content mb-3">
      <div id="navbar">
        <Navbar />
      </div>
      <router-view />
    </div>

    <div id="footer">
      <Footer />
    </div>
  </div>
</template>

<script>
import Navbar from "./components/partials/Navbar";
import Footer from "./components/partials/Footer";

export default {
  name: "App",
  components: {
    Navbar,
    Footer,
  },
};
</script>

<style lang="scss">
@import url("https://fonts.googleapis.com/css2?family=Raleway:ital,wght@0,400;0,900;1,900&display=swap");
* {
  box-sizing: border-box;
}

html,
body {
  height: 100%;
}

body {
  margin: 0;
}

#app {
  display: flex;
  flex-direction: column;
  height: 100%;
  font-family: "Raleway", sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
}

.content {
  flex: 1 0 auto;
}

#navbar {
  font-weight: 900;
  box-shadow: 0 8px 6px -6px #60d69a;
  margin-bottom: 2rem;
  font-size: 1.3rem;
}

#footer {
  font-weight: 900;
  flex-shrink: 0;
}
</style>
