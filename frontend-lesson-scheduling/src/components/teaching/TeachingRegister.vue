<template>
  <div>
    <b-card title="Registrazione Insegnamento" class="ml-3 mr-5">
      <b-form @submit="onSubmit">
        <b-row class="mt-4 mb-3">
          <b-col>
            <b-form-group
              id="input-group-3"
              label="Docente:"
              label-for="input-3"
            >
              <b-form-select
                v-model="teacher"
                :options="teachers"
                value-field="value"
                text-field="text"
                required
                @change="filterCourse"
              >
                <template #first>
                  <b-form-select-option :value="null" disabled
                    >-- Selezionare un docente --</b-form-select-option
                  >
                </template></b-form-select
              >
            </b-form-group>
          </b-col>
          <b-col>
            <b-form-group id="input-group-3" label="Corso:" label-for="input-3">
              <b-form-select
                v-model="course"
                :options="courses"
                value-field="value"
                text-field="text"
                required
                :disabled="courseDisabled"
                ><template #first>
                  <b-form-select-option :value="null" disabled
                    >-- Selezionare un docente --</b-form-select-option
                  >
                </template></b-form-select
              >
            </b-form-group>
          </b-col>
        </b-row>

        <b-button class="btn btn-block mb-3" type="submit">
          Registra
        </b-button>
      </b-form>
    </b-card>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
  data() {
    return {
      teacher: [],
      courseDisabled: true,
      course: [],
    };
  },

  created: function() {
    this.GetActiveTeachers();
  },

  computed: {
    ...mapGetters({
      ActiveTeacherList: "StateActiveTeachers",
      FilteredCourseList: "StateCourseNotTaughtByTeacher",
    }),

    teachers() {
      return this.ActiveTeacherList.map((teacher) => ({
        text: teacher.name + " " + teacher.surname,
        value: teacher,
      }));
    },

    courses() {
      if (this.FilteredTimeSlotList === null) {
        return [];
      }
      return this.FilteredCourseList.map((course) => ({
        text: course.title,
        value: course,
      }));
    },
  },

  methods: {
    ...mapActions([
      "GetActiveTeachers",
      "GetCourseNotTaughtByTeacher",
      "AssignTeaching",
    ]),

    makeToast(variant = null, title, content) {
      this.$bvToast.toast(content, {
        variant: variant,
        title: title,
        solid: true,
      });
    },

    async filterCourse() {
      const Teacher = {
        teacherEmail: this.teacher.email,
      };
      try {
        await this.GetCourseNotTaughtByTeacher(Teacher);
        this.courseDisabled = false;
      } catch {
        console.log("ERROR");
      }
    },

    async onSubmit(event) {
      event.preventDefault();

      const newTeacherCourse = {
        teacherEmail: this.teacher.email,
        title: this.course.title,
      };

      try {
        await this.AssignTeaching(newTeacherCourse);
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
form {
  text-align: left !important;
  align-items: center !important;
}

.btn {
  border-style: none;
  background: #017d91 !important;
}

.btn:hover {
  border-color: #60d69a !important;
  background: #60d69a !important;
  transition: 0.5s all ease-in-out;
}
</style>
