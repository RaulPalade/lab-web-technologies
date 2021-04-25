<template>
  <b-card
    :title="course.title"
    :img-src="webDevImage"
    img-alt="Image"
    img-top
    id="course-card"
  >
    <!-- <b-button id="btn">Prenota</b-button> -->
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
<style>
#course-card {
  height: 330px;
  transition: 0.5s all ease-in-out;
}

#course-card:hover {
  box-shadow: 0 8px 6px -6px #60d69a;
}

#course-card #btn {
  margin-bottom: 0;
}

#btn {
  background: #017d91;
  border-style: none;
}

#btn:hover {
  background: #60d69a;
}
</style>