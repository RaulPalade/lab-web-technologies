<template>
  <div class="user-list">
    <h3>Lista Utenti</h3>
    <b-container fluid>
      <!-- User Interface controls -->
      <b-row>
        <b-col lg="4" class="my-1">
          <b-form-group
            label="Ordina per"
            label-for="sort-by-select"
            label-cols-sm="2"
            label-align-sm="right"
            label-size="sm"
            class="mb-0"
            v-slot="{ ariaDescribedby }"
          >
            <b-input-group size="sm">
              <b-form-select
                id="sort-by-select"
                v-model="sortBy"
                :options="sortOptions"
                :aria-describedby="ariaDescribedby"
                class="w-50"
              >
                <template #first>
                  <option value="">--</option>
                </template>
              </b-form-select>

              <b-form-select
                v-model="sortDesc"
                :disabled="!sortBy"
                :aria-describedby="ariaDescribedby"
                size="sm"
                class="w-25"
              >
                <option :value="false">Crescente</option>
                <option :value="true">Decrescente</option>
              </b-form-select>
            </b-input-group>
          </b-form-group>
        </b-col>

        <b-col lg="4" class="my-1">
          <b-form-group
            label="Cerca"
            label-for="filter-input"
            label-cols-sm="3"
            label-align-sm="right"
            label-size="sm"
            class="mb-0"
          >
            <b-input-group size="sm">
              <b-form-input
                id="filter-input"
                v-model="filter"
                type="search"
                placeholder="Scrivi per cercare..."
              ></b-form-input>

              <b-input-group-append>
                <b-button :disabled="!filter" @click="filter = ''"
                  >Clear</b-button
                >
              </b-input-group-append>
            </b-input-group>
          </b-form-group>
        </b-col>

        <b-col lg="4" class="my-1">
          <b-form-group v-slot="{ ariaDescribedby }">
            <b-form-radio-group
              id="radio-group-1"
              :aria-describedby="ariaDescribedby"
              name="radio-options"
            ></b-form-radio-group>
            <b-form-radio-group
              id="radio-group-2"
              :aria-describedby="ariaDescribedby"
              name="radio-sub-component"
            >
              <b-form-radio value="first" default>Attivi</b-form-radio>
              <b-form-radio value="second">Non Attivi</b-form-radio>
            </b-form-radio-group>
          </b-form-group>
        </b-col>
      </b-row>

      <!-- Main table element -->
      <b-table
        :items="ActiveUserList"
        :fields="fields"
        :current-page="currentPage"
        :per-page="perPage"
        :filter="filter"
        :filter-included-fields="filterOn"
        :sort-by.sync="sortBy"
        :sort-desc.sync="sortDesc"
        :sort-direction="sortDirection"
        stacked="md"
        show-empty
        @filtered="onFiltered"
      >
        <template #cell(cellName)="row">
          {{ row.value.first }} {{ row.value.last }}
        </template>

        <template #cell(actions)="row">
          <b-button
            size="sm"
            @click="info(row.item, row.index, $event.target)"
            class="mr-1"
          >
            Info modal
          </b-button>
          <b-button size="sm" @click="row.toggleDetails">
            {{ row.detailsShowing ? "Hide" : "Show" }} Details
          </b-button>
        </template>

        <template #row-details="row">
          <b-card>
            <ul>
              <li v-for="(value, key) in row.item" :key="key">
                {{ key }}: {{ value }}
              </li>
            </ul>
          </b-card>
        </template>
      </b-table>

      <b-col class="my-1">
        <b-pagination
          v-model="currentPage"
          :total-rows="totalRows"
          :per-page="perPage"
          align="fill"
          size="sm"
          class="my-0"
        ></b-pagination>
      </b-col>

      <b-col sm="4" class="my-1">
        <b-form-group
          label="Per pagina"
          label-for="per-page-select"
          label-cols-sm="6"
          label-cols-md="4"
          label-cols-lg="3"
          label-align-sm="right"
          label-size="sm"
          class="mb-0"
        >
          <b-form-select
            id="per-page-select"
            v-model="perPage"
            :options="pageOptions"
            size="sm"
          ></b-form-select>
        </b-form-group>
      </b-col>

      <!-- Info modal -->
      <b-modal
        :id="infoModal.id"
        :title="infoModal.title"
        ok-only
        @hide="resetInfoModal"
      >
        <pre>{{ infoModal.content }}</pre>
      </b-modal>
    </b-container>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
export default {
  name: "ActiveUserList",
  mounted() {
    this.totalRows = this.ActiveUserList.length;
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
    sortOptions() {
      // Create an options list from our fields
      return this.fields
        .filter((f) => f.sortable)
        .map((f) => {
          return { text: f.label, value: f.key };
        });
    },
  },
  methods: {
    ...mapActions(["GetActiveUsers", "GetDeactivatedUsers"]),
    info(item, index, button) {
      this.infoModal.title = `Row index: ${index}`;
      this.infoModal.content = JSON.stringify(item, null, 2);
      this.$root.$emit("bv::show::modal", this.infoModal.id, button);
    },
    resetInfoModal() {
      this.infoModal.title = "";
      this.infoModal.content = "";
    },
    onFiltered(filteredItems) {
      // Trigger pagination to update the number of buttons/pages due to filtering
      this.totalRows = filteredItems.length;
      this.currentPage = 1;
    },
  },

  data() {
    return {
      fields: [
        { key: "name", label: "Nome", sortable: true },
        { key: "surname", label: "Cognome", sortable: true },
        { key: "email", label: "E-mail", sortable: true },
        { key: "administrator", label: "Amministratore", sortable: true },
      ],
      totalRows: 1,
      currentPage: 1,
      perPage: 5,
      pageOptions: [5, 10, 15, { value: 100, text: "Show a lot" }],
      sortBy: "",
      sortDesc: false,
      sortDirection: "asc",
      filter: null,
      filterOn: [],
      infoModal: {
        id: "info-modal",
        title: "",
        content: "",
      },
    };
  },
};
</script>

<style></style>
