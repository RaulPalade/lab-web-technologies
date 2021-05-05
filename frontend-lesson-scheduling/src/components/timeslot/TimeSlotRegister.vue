<template>
  <div>
    <b-card title="Registrazione Slot Orario" class="ml-3 mr-5">
      <b-form @submit="onSubmit">
        <b-row class="mt-4 mb-3">
          <b-col>
            <b-form-group id="input-group-2" label="Nome:" label-for="input-2">
              <b-form-input
                id="input-2"
                v-model="form.day"
                placeholder="Inserisci il giorno"
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
                v-model="form.hour"
                placeholder="Inserisci l'ora"
                required
              >
              </b-form-input>
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
        day: "Sabato",
        hour: "15",
      },
    };
  },
  methods: {
    ...mapActions(["InsertTimeSlot"]),

    makeToast(variant = null, title, content) {
      this.$bvToast.toast(content, {
        variant: variant,
        title: title,
        solid: true,
      });
    },

    async onSubmit(event) {
      event.preventDefault();

      const newTimeSlot = {
        day: this.form.day,
        hour: this.form.hour,
      };

      try {
        await this.InsertTimeSlot(newTimeSlot);
        this.makeToast(
          "success",
          "Operazione completata",
          "Il nuovo slot orario è stato registrato correttamente"
        );
      } catch {
        this.makeToast(
          "danger",
          "Operazione completata",
          "Impossibile registrare il nuovo slot orario al momento"
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
