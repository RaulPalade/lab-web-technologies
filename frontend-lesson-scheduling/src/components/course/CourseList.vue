<template>
  <div class="time-slot-list">
    <b-tabs content-class="mt-3" v-model="tabActive" @changed="onTabChanged">
      <b-tab title="Corsi attivi" :title-link-class="linkClass(0)">
        <CourseTable :courseList="ActiveCourseList" :active="tabActive" />
      </b-tab>
      <b-tab title="Corsi rimossi" :title-link-class="linkClass(1)">
        <CourseTable :courseList="DeactivatedCourseList" :active="tabActive" />
      </b-tab>
    </b-tabs>
  </div>
</template>

<script>
import CourseTable from "../course/CourseTable";
import { mapGetters, mapActions } from "vuex";
export default {
  components: {
    CourseTable,
  },
  data() {
    return { tabActive: 0, tabs: [] };
  },
  created: function() {
    this.GetActiveCourses();
    this.GetDeactivatedCourses();
  },
  computed: {
    ...mapGetters({
      ActiveCourseList: "StateActiveCourses",
      DeactivatedCourseList: "StateDeactivatedCourses",
    }),
  },
  methods: {
    ...mapActions(["GetActiveCourses", "GetDeactivatedCourses"]),
    onTabChanged() {
      this.tabActive = this.tabs.length - 1;
    },

    linkClass(idx) {
      if (this.tabActive === idx) {
        return "active-tab";
      } else {
        return "not-active-tab";
      }
    },
  },
};
</script>

<style>
.active-tab {
  background: #017d91 !important;
  border-color: #017d91 !important;
  color: #fff !important;
}

.not-active-tab {
  background: rgb(1, 125, 145, 0.1) !important;
  color: #000;
}
</style>
