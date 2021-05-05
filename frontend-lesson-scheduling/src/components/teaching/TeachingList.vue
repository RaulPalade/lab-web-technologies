<template>
  <div class="teacher-list">
    <b-tabs
      class="ml-3 mr-5"
      content-class="mt-3"
      v-model="tabActive"
      @changed="onTabChanged"
    >
      <b-tab title="Insegnamenti attivi" :title-link-class="linkClass(0)">
        <b-row class="mt-4 mb-3">
          <b-col>
            <b-form-group id="input-group-3" label="Corso:" label-for="input-3">
              <b-form-select
                v-model="course"
                :options="courses"
                value-field="value"
                text-field="text"
                required
                @change="filterActiveTeacher"
              >
                <template #first>
                  <b-form-select-option :value="null" disabled
                    >-- Selezionare un corso --</b-form-select-option
                  >
                </template></b-form-select
              >
            </b-form-group>
          </b-col>
        </b-row>
        <b-row cols="2" align-v="start">
          <b-col v-for="teacher in activeTeachers" :key="teacher.id">
            <b-card :title="teacher.text" class="m-2">
              <span v-if="isAdmin">
                <b-button
                  v-b-tooltip.hover
                  title="Rimuovi il corso"
                  @click="removeAssignment(teacher.value)"
                  variant="danger"
                  >Rimuovi
                </b-button>
              </span>
            </b-card>
          </b-col>
        </b-row>
      </b-tab>

      <b-tab title="Insegnamenti rimossi" :title-link-class="linkClass(1)">
        <b-row class="mt-4 mb-3">
          <b-col>
            <b-form-group id="input-group-3" label="Corso:" label-for="input-3">
              <b-form-select
                v-model="course"
                :options="courses"
                value-field="value"
                text-field="text"
                required
                @change="filterDeactivatedTeacher"
              >
                <template #first>
                  <b-form-select-option :value="null" disabled
                    >-- Selezionare un corso --</b-form-select-option
                  >
                </template></b-form-select
              >
            </b-form-group>
          </b-col>
        </b-row>
        <b-row cols="2" align-v="start">
          <b-col v-for="teacher in deactivatedTeachers" :key="teacher.id">
            <b-card :title="teacher.text" class="m-2">
              <span v-if="isAdmin">
                <b-button
                  v-b-tooltip.hover
                  title="Rimuovi il corso"
                  @click="insertAssignment(teacher.value)"
                  variant="success"
                  >Attiva
                </b-button></span
              >
            </b-card>
          </b-col>
        </b-row>
      </b-tab>
    </b-tabs>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
export default {
  data() {
    return {
      tabActive: 0,
      tabs: [],
      teacher: null,
      course: null,
    };
  },
  created: function() {
    this.GetActiveCourses();
  },
  computed: {
    ...mapGetters({
      ActiveCourseList: "StateActiveCourses",
      FilteredActiveTeacherList: "StateActiveTeacherByCourse",
      FilteredDeactivatedTeacherList: "StateDeactivatedTeacherByCourse",
    }),

    isAdmin: function() {
      return this.$store.getters.isAdmin;
    },

    courses() {
      return this.ActiveCourseList.map((course) => ({
        text: course.title,
        value: course,
      }));
    },

    activeTeachers() {
      if (this.FilteredActiveTeacherList === null) {
        return [];
      }
      return this.FilteredActiveTeacherList.map((teacher) => ({
        text: teacher.name + " " + teacher.surname,
        value: teacher,
      }));
    },

    deactivatedTeachers() {
      if (this.FilteredDeactivatedTeacherList === null) {
        return [];
      }
      return this.FilteredDeactivatedTeacherList.map((teacher) => ({
        text: teacher.name + " " + teacher.surname,
        value: teacher,
      }));
    },
  },
  methods: {
    ...mapActions([
      "GetActiveCourses",
      "GetActiveTeacherByCourse",
      "GetDeactivatedTeacherByCourse",
      "DeactivateTeaching",
      "ActivateTeaching",
    ]),

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

    makeToast(variant = null, title, content) {
      this.$bvToast.toast(content, {
        variant: variant,
        title: title,
        solid: true,
      });
    },

    async filterActiveTeacher() {
      const Course = {
        title: this.course.title,
      };
      try {
        await this.GetActiveTeacherByCourse(Course);
        this.teacherDisabled = false;
      } catch {
        console.log("ERROR");
      }
    },

    async filterDeactivatedTeacher() {
      const Course = {
        title: this.course.title,
      };
      try {
        await this.GetDeactivatedTeacherByCourse(Course);
        this.teacherDisabled = false;
      } catch {
        console.log("ERROR");
      }
    },

    async removeAssignment(teacher) {
      const TeacherCourse = {
        teacherEmail: teacher.email,
        title: this.course.title,
      };

      console.log(TeacherCourse);

      try {
        await this.DeactivateTeaching(TeacherCourse);
        this.makeToast(
          "success",
          "Operazione completata",
          "Il nuovo slot orario è stato registrato correttamente"
        );
      } catch {
        this.makeToast(
          "danger",
          "Operazione completata",
          "Impossibile registrare il nuovo slot orario al momento"
        );
      }
    },

    async insertAssignment(teacher) {
      const TeacherCourse = {
        teacherEmail: teacher.email,
        title: this.course.title,
      };

      console.log(TeacherCourse);

      try {
        await this.ActivateTeaching(TeacherCourse);
        this.makeToast(
          "success",
          "Operazione completata",
          "Il nuovo slot orario è stato registrato correttamente"
        );
      } catch {
        this.makeToast(
          "danger",
          "Operazione completata",
          "Impossibile registrare il nuovo slot orario al momento"
        );
      }
    },
  },
};
</script>

<style scoped>
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
