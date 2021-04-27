<template>
  <div class="user-list">
    <b-tabs content-class="mt-3" v-model="tabActive" @changed="onTabChanged">
      <b-tab title="Utenti attivi">
        <UserTable :userList="ActiveUserList" :active="tabActive" />
      </b-tab>
      <b-tab title="Utenti rimossi">
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

    onUpdate() {
      this.$refs.table.refresh();
    },
  },
};
</script>

<style></style>
