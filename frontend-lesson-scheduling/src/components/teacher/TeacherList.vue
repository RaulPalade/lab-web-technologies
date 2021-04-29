<template>
  <div class="teacher-list">
    <b-tabs content-class="mt-3" v-model="tabActive" @changed="onTabChanged">
      <b-tab title="Docenti attivi" :title-link-class="linkClass(0)">
        <TeacherTable :teacherList="ActiveTeacherList" :active="tabActive" />
      </b-tab>
      <b-tab title="Docenti rimossi" :title-link-class="linkClass(1)">
        <TeacherTable
          :teacherList="DeactivatedTeacherList"
          :active="tabActive"
        />
      </b-tab>
    </b-tabs>
  </div>
</template>

<script>
import TeacherTable from "../teacher/TeacherTable";
import { mapGetters, mapActions } from "vuex";
export default {
  components: {
    TeacherTable,
  },
  data() {
    return { tabActive: 0, tabs: [] };
  },
  created: function() {
    this.GetActiveTeachers();
    this.GetDeactivatedTeachers();
  },
  computed: {
    ...mapGetters({
      ActiveTeacherList: "StateActiveTeachers",
      DeactivatedTeacherList: "StateDeactivatedTeachers",
    }),
  },
  methods: {
    ...mapActions(["GetActiveTeachers", "GetDeactivatedTeachers"]),
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
