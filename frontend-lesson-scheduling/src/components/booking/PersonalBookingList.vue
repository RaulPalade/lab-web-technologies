<template>
  <div class="user-list">
    <b-tabs content-class="mt-3" v-model="tabActive" @changed="onTabChanged">
      <b-tab title="Prenotazioni Attive" :title-link-class="linkClass(0)">
        <PersonalBookingTable
          :bookingList="PersonalActiveBookings"
          :active="tabActive"
        />
      </b-tab>
      <b-tab title="Prenotazioni Completate" :title-link-class="linkClass(1)">
        <PersonalBookingTable
          :bookingList="PersonalCompletedBookings"
          :active="tabActive"
        />
      </b-tab>
      <b-tab title="Prenotazioni Cancellate" :title-link-class="linkClass(2)">
        <PersonalBookingTable
          :bookingList="PersonalDeletedBookings"
          :active="tabActive"
        />
      </b-tab>
    </b-tabs>
  </div>
</template>

<script>
import PersonalBookingTable from "../booking/PersonalBookingTable";
import { mapGetters, mapActions } from "vuex";
export default {
  components: {
    PersonalBookingTable,
  },
  data() {
    return { tabActive: 0, tabs: [] };
  },
  created: function() {
    this.GetPersonalActiveBooking();
    this.GetPersonalCompletedBooking();
    this.GetPersonalDeletedBooking();
  },
  computed: {
    ...mapGetters({
      PersonalActiveBookings: "StatePersonalActiveBookings",
      PersonalCompletedBookings: "StatePersonalCompletedBookings",
      PersonalDeletedBookings: "StatePersonalDeletedBookings",
    }),
  },
  methods: {
    ...mapActions([
      "GetPersonalActiveBooking",
      "GetPersonalCompletedBooking",
      "GetPersonalDeletedBooking",
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
