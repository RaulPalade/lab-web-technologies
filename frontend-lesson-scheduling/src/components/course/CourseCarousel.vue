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
              <p>
                <router-link :to="'/course/' + course.title">
                  <CourseCard :course="course" />
                </router-link>
              </p>
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
