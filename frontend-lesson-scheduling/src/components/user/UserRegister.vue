<template>
  <div>
    <b-card title="Registrazione Utente" class="ml-3 mr-5">
      <b-form @submit="onSubmit">
        <b-row class="mt-4 mb-3">
          <b-col>
            <b-form-group id="input-group-2" label="Nome:" label-for="input-2">
              <b-form-input
                id="input-2"
                v-model="form.name"
                placeholder="Inserisci il nome"
                required
              ></b-form-input>
            </b-form-group>
          </b-col>
          <b-col>
            <b-form-group
              id="input-group-2"
              label="Cognome:"
              label-for="input-2"
            >
              <b-form-input
                id="input-2"
                v-model="form.surname"
                placeholder="Inserisci il cognome"
                required
              >
              </b-form-input>
            </b-form-group>
          </b-col>
        </b-row>
        <b-row class="mb-3">
          <b-col>
            <b-form-group
              col="6"
              id="input-group-1"
              label="Email:"
              label-for="input-1"
            >
              <b-form-input
                id="input-1"
                v-model="form.email"
                type="email"
                placeholder="Inserisci l'e-mail"
                required
              ></b-form-input>
            </b-form-group>
          </b-col>
        </b-row>
        <b-row class="mb-3">
          <b-col>
            <b-form-group
              id="input-group-2"
              label="Amministratore:"
              label-for="input-2"
            >
              <b-form-checkbox
                v-model="isAdmin"
                name="check-button"
                switch
                size="lg"
                class="switch"
                ><span v-if="isAdmin">Si</span>
                <span v-else>No</span>
              </b-form-checkbox>
            </b-form-group>
          </b-col>
        </b-row>
        <b-button class="btn btn-block mb-3" type="submit">
          Registra
        </b-button>
      </b-form>
    </b-card>
  </div>
</template>

<script>
import { mapActions } from "vuex";

export default {
  data() {
    return {
      form: {
        name: "Nicola",
        surname: "Baccillieri",
        email: "nicola.baccillieri@edu.unito.it",
      },
      isAdmin: true,
    };
  },
  methods: {
    ...mapActions(["InsertUser"]),

    makeToast(variant = null, title, content) {
      this.$bvToast.toast(content, {
        variant: variant,
        title: title,
        solid: true,
      });
    },

    async onSubmit(event) {
      event.preventDefault();

      const newUser = {
        userName: this.form.name,
        userSurname: this.form.surname,
        userEmail: this.form.email,
        administrator: this.isAdmin,
      };

      try {
        await this.InsertUser(newUser);
        this.makeToast(
          "success",
          "Operazione completata",
          "Il nuovo utente è stato registrato correttamente"
        );
      } catch {
        this.makeToast(
          "danger",
          "Operazione completata",
          "Impossibile registrare il nuovo utente al momento"
        );
      }
    },
  },
};
</script>

<style scoped>
form {
  text-align: left !important;
  align-items: center !important;
}

.custom-control-input:checked ~ .custom-control-label::before {
  color: #fff !important;
  border-color: #017d91 !important;
  background-color: #017d91 !important;
}

.btn {
  border-style: none !important;
  background: #017d91 !important;
}

.btn:hover {
  border-color: #60d69a !important;
  background: #60d69a !important;
  transition: 0.5s all ease-in-out;
}
</style>
