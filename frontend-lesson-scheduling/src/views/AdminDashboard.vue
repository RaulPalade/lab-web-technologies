<template>
  <div class="admin-dashboard">
    <b-container class="d-inline bv-example-row">
      <b-row>
        <b-col sm="3" class="x">
          <b-list-group>
            <b-list-group-item
              button
              class="d-flex justify-content-between align-items-center"
            >
              Lista Prenotazioni
              <b-badge variant="primary" pill>14</b-badge>
            </b-list-group-item>

            <b-list-group-item
              button
              class="d-flex justify-content-between align-items-center"
            >
              Lista Utenti
              <b-badge variant="primary" pill>2</b-badge>
            </b-list-group-item>

            <b-list-group-item
              button
              class="d-flex justify-content-between align-items-center"
            >
              Lista Docenti
              <b-badge variant="primary" pill>1</b-badge>
            </b-list-group-item>

            <b-list-group-item
              button
              class="d-flex justify-content-between align-items-center"
            >
              Lista Corsi
              <b-badge variant="primary" pill>1</b-badge>
            </b-list-group-item>

            <b-list-group-item
              button
              class="d-flex justify-content-between align-items-center"
            >
              Lista Insegnamenti
              <b-badge variant="primary" pill>1</b-badge>
            </b-list-group-item>

            <b-list-group-item
              button
              class="d-flex justify-content-between align-items-center"
            >
              Lista Slot Orari
              <b-badge variant="primary" pill>1</b-badge>
            </b-list-group-item>

            <b-list-group-item
              button
              class="d-flex justify-content-between align-items-center"
            >
              Inserisci Utente
              <b-badge variant="primary" pill>1</b-badge>
            </b-list-group-item>

            <b-list-group-item
              button
              class="d-flex justify-content-between align-items-center"
            >
              Inserisci Docente
              <b-badge variant="primary" pill>1</b-badge>
            </b-list-group-item>

            <b-list-group-item
              button
              class="d-flex justify-content-between align-items-center"
            >
              Inserisci Corso
              <b-badge variant="primary" pill>1</b-badge>
            </b-list-group-item>

            <b-list-group-item
              button
              class="d-flex justify-content-between align-items-center"
            >
              Inserisci Insegnamento
              <b-badge variant="primary" pill>1</b-badge>
            </b-list-group-item>

            <b-list-group-item
              button
              class="d-flex justify-content-between align-items-center"
            >
              Inserisci Slot Orario
              <b-badge variant="primary" pill>1</b-badge>
            </b-list-group-item>
          </b-list-group>
        </b-col>
        <b-col sm="9" class="x">col-sm-4</b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
export default {
  name: "AdminDashboard",
};
</script>

<style>
.x {
  border-style: dotted;
}
</style>