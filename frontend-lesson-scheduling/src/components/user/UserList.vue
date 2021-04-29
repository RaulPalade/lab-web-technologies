<template>
  <div class="user-list">
    <b-tabs content-class="mt-3" v-model="tabActive" @changed="onTabChanged">
      <b-tab title="Utenti attivi" :title-link-class="linkClass(0)">
        <UserTable :userList="ActiveUserList" :active="tabActive" />
      </b-tab>
      <b-tab title="Utenti rimossi" :title-link-class="linkClass(1)">
        <UserTable :userList="DeactivatedUserList" :active="tabActive" />
      </b-tab>
    </b-tabs>
  </div>
</template>

<script>
import UserTable from "../user/UserTable";
import { mapGetters, mapActions } from "vuex";
export default {
  components: {
    UserTable,
  },
  data() {
    return { tabActive: 0, tabs: [] };
  },
  created: function() {
    this.GetActiveUsers();
    this.GetDeactivatedUsers();
  },
  computed: {
    ...mapGetters({
      ActiveUserList: "StateActiveUsers",
      DeactivatedUserList: "StateDeactivatedUsers",
    }),
  },
  methods: {
    ...mapActions(["GetActiveUsers", "GetDeactivatedUsers"]),
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
