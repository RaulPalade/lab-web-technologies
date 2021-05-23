<template>
  <div class="cards">
    <b-container class="mt-3 d-inline align-items-stretch">
      <b-row>
        <b-col>
          <carousel autoplay :paginationSize="12" :perPage="4">
            <slide
              v-for="course in courses"
              :course="course"
              :key="course.id"
              class="p-2"
            >
              <span v-if="isAdmin">
                <p>
                  <router-link to="/adminDashboard">
                    <CourseCard :course="course" />
                  </router-link>
                </p>
              </span>
              <span v-else-if="isLoggedIn">
                <p>
                  <router-link to="/userDashboard">
                    <CourseCard :course="course" />
                  </router-link>
                </p>
              </span>
              <span v-else>
                <p>
                  <router-link to="/login">
                    <CourseCard :course="course" />
                  </router-link>
                </p>
              </span>
            </slide>
          </carousel>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import CourseCard from "./CourseCard";
import { mapGetters, mapActions } from "vuex";

export default {
  name: "CoursCarousel",
  components: {
    CourseCard,
  },
  data() {
    return {
      slide: 0,
      sliding: null,
    };
  },

  created: function() {
    this.GetActiveCourses();
  },
  computed: {
    ...mapGetters({ courses: "StateActiveCourses", User: "StateUser" }),
    isLoggedIn: function() {
      return this.$store.getters.isAuthenticated;
    },
    isAdmin: function() {
      return this.$store.getters.isAdmin;
    },
  },
  methods: {
    ...mapActions(["GetActiveCourses"]),
    onSlideStart() {
      this.sliding = true;
    },
    onSlideEnd() {
      this.sliding = false;
    },
  },
};
</script>

<style lang="scss"></style>
