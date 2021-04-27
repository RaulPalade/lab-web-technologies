<template>
  <div>
    <Toasts
      :show-progress="true"
      :rtl="false"
      :max-messages="5"
      :time-out="3000"
      :closeable="true"
    ></Toasts>
    <b-container fluid>
      <!-- User Interface controls -->
      <b-row class="mb-3">
        <b-col lg="4" class="my-1">
          <b-form-group
            label="Ordina per"
            label-for="sort-by-select"
            label-cols-sm="3"
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
                class="w-25"
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

        <b-col lg="5" class="my-1">
          <b-form-group
            label="Cerca"
            label-for="filter-input"
            label-cols-sm="2"
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

        <b-col sm="3" class="my-1">
          <b-form-group
            label="Per pagina"
            label-for="per-page-select"
            label-cols-sm="7"
            label-cols-md="5"
            label-cols-lg="4"
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
      </b-row>

      <!-- Main table element -->
      <b-table
        :items="userList"
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
        ref="table"
      >
        <template #cell(cellName)="row">
          {{ row.value.first }} {{ row.value.last }}
        </template>

        <template v-slot:cell(administrator)="row">
          <span v-if="row.item.administrator">Si</span>
          <span v-else>No</span>
        </template>

        <template #cell(actions)="row">
          <span v-if="active === 0">
            <b-button
              v-b-tooltip.hover
              title="Rimuovi l'utente"
              @click="removeUser(row.item)"
              variant="danger"
            >
              <i class="fas fa-trash-alt icon-deactivate"></i>
            </b-button>
          </span>
          <span v-if="active === 1">
            <b-button
              v-b-tooltip.hover
              title="Attiva l'utente"
              @click="addUser(row.item)"
              variant="success"
            >
              <i class="fas fa-user-plus icon-activate">
                <span v-if="showError">
                  <b-alert show variant="danger"
                    >Errore durante la rimozione dell'utente</b-alert
                  >
                </span>
              </i>
            </b-button>
          </span>
        </template>
      </b-table>

      <b-modal ref="my-modal" hide-footer title="Conferma rimozione">
        <b-button class="mt-2" variant="warning" @click="removeUser(item)">
          Si
        </b-button>
      </b-modal>

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
    </b-container>
  </div>
</template>

<script>
import { mapActions } from "vuex";

export default {
  props: ["userList", "active"],
  data() {
    return {
      showError: false,
      fields: [
        { key: "name", label: "Nome", sortable: true, _rowVariant: "success" },
        { key: "surname", label: "Cognome", sortable: true },
        { key: "email", label: "E-mail", sortable: true },
        { key: "administrator", label: "Amministratore", sortable: true },
        { key: "actions", label: "" },
      ],
      options: [
        { text: "Attivi", value: "first" },
        { text: "Disattivati", value: "second" },
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
  mounted() {
    this.totalRows = this.userList.length;
  },
  computed: {
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
    ...mapActions(["ActivateUser", "DeactivateUser"]),

    async addUser(item) {
      const User = {
        userName: item.name,
        userSurname: item.surname,
        userEmail: item.email,
      };
      try {
        await this.ActivateUser(User);
        this.$toast.success("Utente riattivato");
      } catch (error) {
        this.$toast.success("Errore durante la riattivazione dell'utente");
      }
    },

    async removeUser(item) {
      const User = {
        userName: item.name,
        userSurname: item.surname,
        userEmail: item.email,
      };
      try {
        await this.DeactivateUser(User);
        this.$toast.success("Utente rimosso");
      } catch (error) {
        this.$toast.success("Errore durante la rimozione dell'utente");
      }
    },
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
};
</script>

<style scoped>
.icon-activate {
  background: #28a745;
}
.icon-deactivate {
  background: red;
}
</style>
