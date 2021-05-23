<template>
  <b-card
    :title="course.title"
    :img-src="webDevImage"
    img-alt="Image"
    img-top
    id="course-card"
  >
  </b-card>
</template>
<script>
import webDevImage from "@/assets/courses/web-development.jpg";

export default {
  props: ["course"],
  data() {
    return { webDevImage };
  },
};
</script>
<style scoped>
#course-card {
  height: 330px;
  transition: 0.5s all ease-in-out;
}

h4.card-title {
  color: #017d91;
}

#course-card:hover {
  box-shadow: 0 8px 6px -6px #60d69a;
}
</style>
