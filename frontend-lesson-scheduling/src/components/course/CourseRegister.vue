<template>
  <div>
    <b-card title="Registrazione Corso" class="ml-3 mr-5">
      <b-form @submit="onSubmit">
        <b-row class="mb-3">
          <b-col>
            <b-form-group
              col="6"
              id="input-group-1"
              label="Titolo Corso:"
              label-for="input-1"
            >
              <b-form-input
                id="input-1"
                v-model="form.title"
                type="text"
                placeholder="Inserisci il titolo del corso"
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
        title: "Interazione Uomo Macchina e Tecnologie Web",
      },
    };
  },
  methods: {
    ...mapActions(["InsertCourse"]),

    makeToast(variant = null, title, content) {
      this.$bvToast.toast(content, {
        variant: variant,
        title: title,
        solid: true,
      });
    },

    async onSubmit(event) {
      event.preventDefault();

      const newCourse = {
        title: this.form.title,
      };

      try {
        await this.InsertCourse(newCourse);
        this.makeToast(
          "success",
          "Operazione completata",
          "Il nuovo corso è stato registrato correttamente"
        );
      } catch {
        this.makeToast(
          "danger",
          "Operazione completata",
          "Impossibile registrare il nuovo corso al momento"
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
