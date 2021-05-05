<template>
  <div class="admin-dashboard">
    <b-container class="d-inline bv-example-row">
      <b-row>
        <b-col sm="3">
          <b-list-group class="list-group">
            <b-list-group-item
              button
              v-bind:class="{ active: isSelected(0) }"
              v-on:click="selected = 0"
              class="d-flex align-items-center list-group-item"
            >
              <i class="fas fa-calendar-alt fa-fw mr-2 font-awesome-icon"></i>
              Prenotazioni Mie
            </b-list-group-item>

            <b-list-group-item
              button
              v-bind:class="{ active: isSelected(1) }"
              v-on:click="selected = 1"
              class="d-flex align-items-center"
            >
              <i
                class="fas fa-chalkboard-teacher fa-fw mr-2 font-awesome-icon"
              ></i>
              Lista Docenti
            </b-list-group-item>

            <b-list-group-item
              button
              v-bind:class="{ active: isSelected(2) }"
              v-on:click="selected = 2"
              class="d-flex align-items-center"
            >
              <i class="fas fa-book fa-fw mr-2 font-awesome-icon"></i>
              Lista Corsi
            </b-list-group-item>

            <b-list-group-item
              button
              v-bind:class="{ active: isSelected(3) }"
              v-on:click="selected = 3"
              class="d-flex align-items-center"
            >
              <i class="fas fa-school fa-fw mr-2 font-awesome-icon"></i>
              Insegnamenti
            </b-list-group-item>

            <b-list-group-item
              button
              v-bind:class="{ active: isSelected(4) }"
              v-on:click="selected = 4"
              class="d-flex align-items-center"
            >
              <i class="fas fa-clock fa-fw mr-2 font-awesome-icon"></i>
              Lista Slot Orari
            </b-list-group-item>

            <b-list-group-item
              button
              v-bind:class="{ active: isSelected(5) }"
              v-on:click="selected = 5"
              class="d-flex align-items-center"
            >
              <i class="fas fa-calendar-plus fa-fw mr-2 font-awesome-icon"></i>
              Prenota Ripetizione
            </b-list-group-item>
          </b-list-group>
        </b-col>
        <b-col sm="9">
          <span v-if="selected === 0"><PersonalBookingList /></span>
          <span v-if="selected === 1"><TeacherList /></span>
          <span v-if="selected === 2"><CourseList /></span>
          <span v-if="selected === 3"><TeachingList /></span>
          <span v-if="selected === 4"><TimeSlotList /></span>
          <span v-if="selected === 5"><BookingRegistration /></span>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import PersonalBookingList from "../components/booking/PersonalBookingList";
import BookingRegistration from "../components/booking/BookingRegistration";
import TeacherList from "../components/teacher/TeacherList";
import CourseList from "../components/course/CourseList";
import TeachingList from "../components/teaching/TeachingList";
import TimeSlotList from "../components/timeslot/TimeSlotList";

export default {
  name: "AdminDashboard",
  components: {
    PersonalBookingList,
    BookingRegistration,
    TeacherList,
    CourseList,
    TeachingList,
    TimeSlotList,
  },
  data() {
    return {
      selected: 0,
    };
  },
  methods: {
    isSelected(i) {
      return i === this.selected;
    },
  },
};
</script>

<style lang="scss" scoped>
.list-group {
  font-size: 1.1rem;
}

.active {
  background: #017d91 !important;
  border-color: #017d91 !important;
}

.active .font-awesome-icon {
  color: #fff;
  background: #017d91 !important ;
}
</style>
