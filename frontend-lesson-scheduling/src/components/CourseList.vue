<template>
  <b-container fluid>
    <div v-if="courses">
      <b-row cols="4" align-v="start">
        <b-col v-for="course in courses" :course="course" :key="course.id">
          <p>
            <router-link :to="'/course/' + course.id">
              <CourseCard :course="course" />
            </router-link></p
        ></b-col>
      </b-row>
    </div>
    <div v-else>Nessun corso</div>
  </b-container>
</template>

<script>
// import axios from "axios";
import CourseCard from "./CourseCard";
import { mapGetters, mapActions } from "vuex";

export default {
  name: "CoursList",
  components: {
    CourseCard,
  },
  // data() {
  //   return {
  //     courses: [],
  //   };
  // },
  // mounted() {
  //   axios
  //     .get("http://localhost:8080/ServletController?action=list-active-courses")
  //     .then((response) => (this.courses = response.data))
  //     .catch(function (error) {
  //       console.log(error);
  //     });
  // },
  created: function () {
    this.GetActiveCourses();
  },
  computed: {
    ...mapGetters({ courses: "StateActiveCourses", User: "StateUser" }),
  },
  methods: {
    ...mapActions(["GetActiveCourses"]),
  },
};
</script>

<style scoped>
</style>