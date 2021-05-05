<template>
  <div class="time-slot-list">
    <b-tabs content-class="mt-3" v-model="tabActive" @changed="onTabChanged">
      <b-tab title="Slot orari attivi" :title-link-class="linkClass(0)">
        <TimeSlotTable
          :timeSlotList="ActiveTimeSlotList"
          :active="tabActive"
          :isAdmin="isAdmin"
        />
      </b-tab>
      <b-tab title="Slot orari rimossi" :title-link-class="linkClass(1)">
        <TimeSlotTable
          :timeSlotList="DeactivatedTimeSlotList"
          :active="tabActive"
          :isAdmin="isAdmin"
        />
      </b-tab>
    </b-tabs>
  </div>
</template>

<script>
import TimeSlotTable from "../timeslot/TimeSlotTable";
import { mapGetters, mapActions } from "vuex";
export default {
  components: {
    TimeSlotTable,
  },
  data() {
    return { tabActive: 0, tabs: [] };
  },
  created: function() {
    this.GetActiveTimeSlots();
    this.GetDeactivatedTimeSlots();
  },
  computed: {
    ...mapGetters({
      ActiveTimeSlotList: "StateActiveTimeSlots",
      DeactivatedTimeSlotList: "StateDeactivatedTimeSlots",
    }),

    isAdmin: function() {
      return this.$store.getters.isAdmin;
    },
  },
  methods: {
    ...mapActions(["GetActiveTimeSlots", "GetDeactivatedTimeSlots"]),
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
