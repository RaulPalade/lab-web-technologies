<template>
  <div>
    <b-card title="Registrazione Docente" class="ml-3 mr-5">
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
        name: "Cuciuffo",
        surname: "Gaetano",
        email: "gaetano.cuciuffo@gmail.com",
      },
    };
  },
  methods: {
    ...mapActions(["InsertTeacher"]),

    makeToast(variant = null, title, content) {
      this.$bvToast.toast(content, {
        variant: variant,
        title: title,
        solid: true,
      });
    },

    async onSubmit(event) {
      event.preventDefault();

      const newTeacher = {
        teacherName: this.form.name,
        teacherSurname: this.form.surname,
        teacherEmail: this.form.email,
      };

      try {
        await this.InsertTeacher(newTeacher);
        this.makeToast(
          "success",
          "Operazione completata",
          "Il nuovo docente è stato registrato correttamente"
        );
      } catch {
        this.makeToast(
          "danger",
          "Operazione completata",
          "Impossibile registrare il nuovo docente al momento"
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

.btn {
  border-style: none;
  background: #017d91 !important;
}

.btn:hover {
  border-color: #60d69a !important;
  background: #60d69a !important;
  transition: 0.5s all ease-in-out;
}
</style>
