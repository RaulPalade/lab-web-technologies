<template>
  <div>
    <b-card title="Prenota una ripetizione" class="ml-3 mr-5">
      <b-form @submit="onSubmit">
        <b-row class="mt-4 mb-3">
          <b-col>
            <b-form-group id="input-group-3" label="Corso:" label-for="input-3">
              <b-form-select
                v-model="course"
                :options="courses"
                value-field="value"
                text-field="text"
                required
                @change="filterTeacher"
              >
                <template #first>
                  <b-form-select-option :value="null" disabled
                    >-- Selezionare un corso --</b-form-select-option
                  >
                </template></b-form-select
              >
            </b-form-group>
          </b-col>
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
                :disabled="teacherDisabled"
                @change="filterTimeSlot"
                ><template #first>
                  <b-form-select-option :value="null" disabled
                    >-- Selezionare un docente --</b-form-select-option
                  >
                </template></b-form-select
              >
            </b-form-group>
          </b-col>
        </b-row>
        <b-row class="mt-4 mb-3"
          ><b-col cols="6">
            <b-form-group id="input-group-3" label="Data:" label-for="input-3">
              <b-form-select
                v-model="timeSlot"
                :options="timeSlots"
                value-field="value"
                text-field="text"
                required
                :disabled="timeSlotDisabled"
                ><template #first>
                  <b-form-select-option :value="null" disabled
                    >-- Selezionare una data --</b-form-select-option
                  >
                </template></b-form-select
              >
            </b-form-group>
          </b-col>
        </b-row>

        <b-button class="btn btn-block mb-3" type="submit">
          Prenota
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
      teacher: null,
      teacherDisabled: true,
      timeSlotDisabled: true,
      course: null,
      timeSlot: null,
    };
  },

  created: function() {
    this.GetActiveCourses();
  },

  computed: {
    ...mapGetters({
      ActiveCourseList: "StateActiveCourses",
      FilteredTeacherList: "StateActiveTeacherByCourse",
      FilteredTimeSlotList: "StateBookingAvailabilities",
    }),

    courses() {
      return this.ActiveCourseList.map((course) => ({
        text: course.title,
        value: course,
      }));
    },

    teachers() {
      if (this.FilteredTeacherList === null) {
        return [];
      } else {
        return this.FilteredTeacherList.map((teacher) => ({
          text: teacher.name + " " + teacher.surname,
          value: teacher,
        }));
      }
    },

    timeSlots() {
      if (this.FilteredTimeSlotList === null) {
        return [];
      } else {
        return this.FilteredTimeSlotList.map((timeSlot) => ({
          text: timeSlot.day + " " + timeSlot.hour,
          value: timeSlot,
        }));
      }
    },
  },

  methods: {
    ...mapActions([
      "GetActiveCourses",
      "GetActiveTeacherByCourse",
      "GetBookingAvailability",
      "InsertBooking",
    ]),

    makeToast(variant = null, title, content) {
      this.$bvToast.toast(content, {
        variant: variant,
        title: title,
        solid: true,
      });
    },

    async filterTeacher() {
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

    async filterTimeSlot() {
      const Teacher = {
        teacherEmail: this.teacher.email,
      };
      try {
        await this.GetBookingAvailability(Teacher);
        this.timeSlotDisabled = false;
      } catch {
        console.log("ERROR");
      }
    },

    async onSubmit(event) {
      event.preventDefault();

      const newBooking = {
        day: this.timeSlot.day,
        hour: this.timeSlot.hour,
        teacherEmail: this.teacher.email,
        title: this.course.title,
      };

      try {
        await this.InsertBooking(newBooking);
        this.makeToast(
          "success",
          "Operazione completata",
          "La prenotazione è stato aggiunta"
        );
      } catch {
        this.makeToast(
          "danger",
          "Operazione completata",
          "Impossibile aggiungere la nuova prenotazione"
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
