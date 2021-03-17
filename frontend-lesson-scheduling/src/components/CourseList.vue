<template>
  <b-container fluid>
    <b-row cols="4" align-v="start">
      <b-col v-for="course in courses" :course="course" :key="course.id">
        <router-link :to="'/course/' + course.id">
          <CourseCard :course="course" />
        </router-link>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import axios from "axios";
import CourseCard from "./CourseCard";

export default {
  name: "CoursList",
  components: {
    CourseCard,
  },
  data() {
    return {
      courses: [],
    };
  },
  mounted() {
    axios
      .get("http://localhost:8080/ServletController?action=list-courses")
      .then((response) => (this.courses = response.data))
      .catch(function (error) {
        if (error.response) {
          console.log(error.response.data);
          console.log(error.response.status);
          console.log(error.response.headers);
        }
      });
  },
};
</script>

<style scoped>
</style>