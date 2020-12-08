<template>
  <div id="show-users">
    <h1>USER LIST</h1>
    <div v-bind:key="blog" v-for="blog in blogs" class="single-user"></div>
    <h2>{{ blog.title }}</h2>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {};
  },
  methods: {},
  created() {
    axios.get("https://jsonplaceholder.typicode.com/posts").then((response) => {
      console.log(response.data);
      this.blogs = response.data.slice(0, 10);
    });
  },
};
</script>