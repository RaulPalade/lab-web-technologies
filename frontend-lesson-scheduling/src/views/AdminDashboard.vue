<template>
  <div class="admin-dashboard">
    <b-container class="d-inline bv-example-row">
      <b-row>
        <b-col sm="3">
          <b-list-group>
            <b-list-group-item
              button
              v-bind:class="{ active: isSelected(0) }"
              v-on:click="selected = 0"
              class="d-flex justify-content-between align-items-center"
            >
              Lista Prenotazioni
            </b-list-group-item>

            <b-list-group-item
              button
              v-bind:class="{ active: isSelected(1) }"
              v-on:click="selected = 1"
              class="d-flex justify-content-between align-items-center"
            >
              Lista Utenti
            </b-list-group-item>

            <b-list-group-item
              button
              v-bind:class="{ active: isSelected(2) }"
              v-on:click="selected = 2"
              class="d-flex justify-content-between align-items-center"
            >
              Lista Docenti
            </b-list-group-item>

            <b-list-group-item
              button
              v-bind:class="{ active: isSelected(3) }"
              v-on:click="selected = 3"
              class="d-flex justify-content-between align-items-center"
            >
              Lista Corsi
            </b-list-group-item>

            <b-list-group-item
              button
              v-bind:class="{ active: isSelected(4) }"
              v-on:click="selected = 4"
              class="d-flex justify-content-between align-items-center"
            >
              Lista Insegnamenti
            </b-list-group-item>

            <b-list-group-item
              button
              v-bind:class="{ active: isSelected(5) }"
              v-on:click="selected = 5"
              class="d-flex justify-content-between align-items-center"
            >
              Lista Slot Orari
            </b-list-group-item>

            <b-list-group-item
              button
              v-bind:class="{ active: isSelected(6) }"
              v-on:click="selected = 6"
              class="d-flex justify-content-between align-items-center"
            >
              Inserisci Utente
            </b-list-group-item>

            <b-list-group-item
              button
              v-bind:class="{ active: isSelected(7) }"
              v-on:click="selected = 7"
              class="d-flex justify-content-between align-items-center"
            >
              Inserisci Docente
            </b-list-group-item>

            <b-list-group-item
              button
              v-bind:class="{ active: isSelected(8) }"
              v-on:click="selected = 8"
              class="d-flex justify-content-between align-items-center"
            >
              Inserisci Corso
            </b-list-group-item>

            <b-list-group-item
              button
              v-bind:class="{ active: isSelected(9) }"
              v-on:click="selected = 9"
              class="d-flex justify-content-between align-items-center"
            >
              Inserisci Insegnamento
            </b-list-group-item>

            <b-list-group-item
              button
              v-bind:class="{ active: isSelected(10) }"
              v-on:click="selected = 10"
              class="d-flex justify-content-between align-items-center"
            >
              Inserisci Slot Orario
            </b-list-group-item>
          </b-list-group>
        </b-col>
        <b-col sm="9">
          <span v-if="selected === 0"><BookingList /></span>
          <span v-if="selected === 1"><UserList /></span>
          <span v-if="selected === 2">Lista Docenti</span>
          <span v-if="selected === 3">Lista Corsi</span>
          <span v-if="selected === 4">Lista Insegnamenti</span>
          <span v-if="selected === 5">Lista Slot Orari</span>
          <span v-if="selected === 6">Inserisci Utente</span>
          <span v-if="selected === 7">Inserisci Docente</span>
          <span v-if="selected === 8">Inserisci Corso</span>
          <span v-if="selected === 9">Inserisci Insegnamento</span>
          <span v-if="selected === 10">Inserisci Slot Orario</span>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import BookingList from "../components/BookingList";
import UserList from "../components/UserList";

export default {
  name: "AdminDashboard",
  components: {
    BookingList,
    UserList,
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

<style></style>
