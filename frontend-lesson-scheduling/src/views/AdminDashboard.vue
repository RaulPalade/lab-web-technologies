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
              class="d-flex align-items-center list-group-item"
            >
              <i class="fas fa-calendar-alt fa-fw mr-2 font-awesome-icon"></i>
              Lista Prenotazioni
            </b-list-group-item>

            <b-list-group-item
              button
              v-bind:class="{ active: isSelected(2) }"
              v-on:click="selected = 2"
              class="d-flex align-items-center"
            >
              <i class="fas fa-user-graduate fa-fw mr-2 font-awesome-icon"></i>
              Lista Utenti
            </b-list-group-item>

            <b-list-group-item
              button
              v-bind:class="{ active: isSelected(3) }"
              v-on:click="selected = 3"
              class="d-flex align-items-center"
            >
              <i
                class="fas fa-chalkboard-teacher fa-fw mr-2 font-awesome-icon"
              ></i>
              Lista Docenti
            </b-list-group-item>

            <b-list-group-item
              button
              v-bind:class="{ active: isSelected(4) }"
              v-on:click="selected = 4"
              class="d-flex align-items-center"
            >
              <i class="fas fa-book fa-fw mr-2 font-awesome-icon"></i>
              Lista Corsi
            </b-list-group-item>

            <b-list-group-item
              button
              v-bind:class="{ active: isSelected(5) }"
              v-on:click="selected = 5"
              class="d-flex align-items-center"
            >
              <i class="fas fa-school fa-fw mr-2 font-awesome-icon"></i>
              Insegnamenti
            </b-list-group-item>

            <b-list-group-item
              button
              v-bind:class="{ active: isSelected(6) }"
              v-on:click="selected = 6"
              class="d-flex align-items-center"
            >
              <i class="fas fa-clock fa-fw mr-2 font-awesome-icon"></i>
              Lista Slot Orari
            </b-list-group-item>

            <b-list-group-item
              button
              v-bind:class="{ active: isSelected(7) }"
              v-on:click="selected = 7"
              class="d-flex align-items-center"
            >
              <i class="fas fa-calendar-plus fa-fw mr-2 font-awesome-icon"></i>
              Prenota Ripetizione
            </b-list-group-item>

            <b-list-group-item
              button
              v-bind:class="{ active: isSelected(8) }"
              v-on:click="selected = 8"
              class="d-flex align-items-center"
            >
              <i class="fas fa-user-plus fa-fw mr-2 font-awesome-icon"></i>
              Inserisci Utente
            </b-list-group-item>

            <b-list-group-item
              button
              v-bind:class="{ active: isSelected(9) }"
              v-on:click="selected = 9"
              class="d-flex align-items-center"
            >
              <i class="fas fa-user-plus fa-fw mr-2 font-awesome-icon"></i>
              Inserisci Docente
            </b-list-group-item>

            <b-list-group-item
              button
              v-bind:class="{ active: isSelected(10) }"
              v-on:click="selected = 10"
              class="d-flex align-items-center"
            >
              <i class="fas fa-book-medical fa-fw mr-2 font-awesome-icon"></i>
              Inserisci Corso
            </b-list-group-item>

            <b-list-group-item
              button
              v-bind:class="{ active: isSelected(11) }"
              v-on:click="selected = 11"
              class="d-flex align-items-center"
            >
              <i class="fas fa-plus-circle fa-fw mr-2 font-awesome-icon"></i>
              Inserisci Insegnamento
            </b-list-group-item>

            <b-list-group-item
              button
              v-bind:class="{ active: isSelected(12) }"
              v-on:click="selected = 12"
              class="d-flex  align-items-center"
            >
              <i class="fas fa-plus-circle fa-fw mr-2 font-awesome-icon"></i>
              Inserisci Slot Orario
            </b-list-group-item>
          </b-list-group>
        </b-col>
        <b-col sm="9">
          <span v-if="selected === 0"><PersonalBookingList /></span>
          <span v-if="selected === 1"><BookingList /></span>
          <span v-if="selected === 2"><UserList /></span>
          <span v-if="selected === 3"><TeacherList /></span>
          <span v-if="selected === 4"><CourseList /></span>
          <span v-if="selected === 5"><TeachingList /></span>
          <span v-if="selected === 6"><TimeSlotList /></span>
          <span v-if="selected === 7"><BookingRegistration /></span>
          <span v-if="selected === 8"><UserRegister /></span>
          <span v-if="selected === 9"><TeacherRegister /></span>
          <span v-if="selected === 10"><CourseRegister /></span>
          <span v-if="selected === 11"><TeachingRegister /></span>
          <span v-if="selected === 12"><TimeSlotRegister /></span>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import PersonalBookingList from "../components/booking/PersonalBookingList";
import BookingList from "../components/booking/BookingList";
import BookingRegistration from "../components/booking/BookingRegistration";
import UserList from "../components/user/UserList";
import UserRegister from "../components/user/UserRegister";
import TeacherList from "../components/teacher/TeacherList";
import TeacherRegister from "../components/teacher/TeacherRegister";
import CourseList from "../components/course/CourseList";
import CourseRegister from "../components/course/CourseRegister";
import TeachingList from "../components/teaching/TeachingList";
import TeachingRegister from "../components/teaching/TeachingRegister";
import TimeSlotList from "../components/timeslot/TimeSlotList";
import TimeSlotRegister from "../components/timeslot/TimeSlotRegister";

export default {
  name: "AdminDashboard",
  components: {
    PersonalBookingList,
    BookingList,
    BookingRegistration,
    UserList,
    UserRegister,
    TeacherList,
    TeacherRegister,
    CourseList,
    CourseRegister,
    TeachingList,
    TeachingRegister,
    TimeSlotList,
    TimeSlotRegister,
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
