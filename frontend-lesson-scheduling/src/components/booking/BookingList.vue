<template>
  <div class="user-list">
    <b-tabs content-class="mt-3" v-model="tabActive" @changed="onTabChanged">
      <b-tab title="Prenotazioni Attive" :title-link-class="linkClass(0)">
        <BookingTable :bookingList="ActiveBookings" :active="tabActive" />
      </b-tab>
      <b-tab title="Prenotazioni Completate" :title-link-class="linkClass(1)">
        <BookingTable :bookingList="CompletedBookings" :active="tabActive" />
      </b-tab>
      <b-tab title="Prenotazioni Cancellate" :title-link-class="linkClass(2)">
        <BookingTable :bookingList="DeletedBookings" :active="tabActive" />
      </b-tab>
    </b-tabs>
  </div>
</template>

<script>
import BookingTable from "../booking/BookingTable";
import { mapGetters, mapActions } from "vuex";
export default {
  components: {
    BookingTable,
  },
  data() {
    return { tabActive: 0, tabs: [] };
  },
  created: function() {
    this.GetAllBookings();
    this.GetAllActiveBookings();
    this.GetAllCompletedBookings();
    this.GetAllDeletedBookings();
  },
  computed: {
    ...mapGetters({
      Bookings: "StateAllBookings",
      ActiveBookings: "StateAllActiveBookings",
      CompletedBookings: "StateAllCompletedBookings",
      DeletedBookings: "StateAllDeletedBookings",
    }),
  },
  methods: {
    ...mapActions([
      "GetAllBookings",
      "GetAllActiveBookings",
      "GetAllCompletedBookings",
      "GetAllDeletedBookings",
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
