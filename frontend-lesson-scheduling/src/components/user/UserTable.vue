<template>
  <div>
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
        empty-text="Non ci sono utenti da mostrare"
        head-variant="light"
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

        <template #cell(actions)="row">
          <span v-if="active === 0">
            <b-button
              v-b-tooltip.hover
              title="Rimuovi l'utente"
              @click="removeRow(row.item)"
              variant="danger"
            >
              <i class="fas fa-trash-alt"></i>
            </b-button>
          </span>
          <span v-if="active === 1">
            <b-button
              v-b-tooltip.hover
              title="Attiva l'utente"
              @click="addRow(row.item)"
              variant="success"
            >
              <i class="fas fa-user-plus"> </i>
            </b-button>
          </span>
        </template>
      </b-table>

      <b-col class="my-1">
        <b-pagination
          v-model="currentPage"
          :total-rows="totalRows"
          :per-page="perPage"
          align="fill"
          size="sm"
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
      fields: [
        { key: "name", label: "Nome", sortable: true, _rowVariant: "success" },
        { key: "surname", label: "Cognome", sortable: true },
        { key: "email", label: "E-mail", sortable: true },
        { key: "actions", label: "" },
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
    };
  },

  mounted() {
    if (this.userList === null) {
      this.totalRows = 0;
    } else {
      this.totalRows = this.userList.length;
    }
  },

  beforeUpdate() {
    if (this.userList === null) {
      this.totalRows = 0;
    } else {
      this.totalRows = this.userList.length;
    }
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

    makeToast(variant = null, title, content) {
      this.$bvToast.toast(content, {
        variant: variant,
        title: title,
        solid: true,
      });
    },

    async addRow(item) {
      const User = {
        userName: item.name,
        userSurname: item.surname,
        userEmail: item.email,
      };
      try {
        await this.ActivateUser(User);
        this.makeToast(
          "success",
          "Operazione completata",
          "L'utente è stato riattivato"
        );
      } catch (error) {
        this.makeToast("danger", "Errore", "Impossibile riattivare l'utente");
      }
    },

    async removeRow(item) {
      const User = {
        userName: item.name,
        userSurname: item.surname,
        userEmail: item.email,
      };
      try {
        await this.DeactivateUser(User);
        this.makeToast(
          "success",
          "Operazione completata",
          "L'utente è stato rimosso"
        );
      } catch (error) {
        this.makeToast("danger", "Errore", "Impossibile rimuovere l'utente");
      }
    },

    onFiltered(filteredItems) {
      // Trigger pagination to update the number of buttons/pages due to filtering
      this.totalRows = filteredItems.length;
      this.currentPage = 1;
    },
  },
};
</script>

<style lang="scss">
.page-link {
  color: #017d91 !important;
}

.page-item.active .page-link {
  z-index: 3;
  color: #fff;
  background-color: #017d91 !important;
  border-color: #017d91 !important;
  color: #fff !important;
}
</style>
