<template>
  <div class="mb-3">
    <b-card title="Accedi al tuo account" class="login w-25">
      <form @submit.prevent="submit">
        <b-form-group class="my-5" label="Email" label-for="email">
          <b-form-input
            v-model="form.email"
            type="email"
            placeholder="Inserisci l'email"
            required
          ></b-form-input>
        </b-form-group>

        <b-form-group class="my-5" label="Password" label-for="password">
          <b-form-input
            v-model="form.password"
            type="password"
            placeholder="Inserisci la password"
            required
          ></b-form-input>
        </b-form-group>
        <b-button class="btn my-3 py-2 btn-block" type="submit"
          >Accedi</b-button
        >
      </form>
    </b-card>
  </div>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "Login",
  components: {},
  data() {
    return {
      form: {
        email: "raul.palade@edu.unito.it",
        password: "password1",
      },
    };
  },

  computed: {
    isAdmin: function() {
      return this.$store.getters.isAdmin;
    },
  },

  methods: {
    ...mapActions(["LogIn"]),

    makeToast(variant = null, title, content) {
      this.$bvToast.toast(content, {
        variant: variant,
        title: title,
        solid: true,
      });
    },

    async submit(event) {
      event.preventDefault();

      const User = {
        email: this.form.email,
        password: this.form.password,
      };
      try {
        await this.LogIn(User);
        if (this.isAdmin) {
          this.$router.push("/adminDashboard");
        } else {
          this.$router.push("/userDashboard");
        }
      } catch (error) {
        this.makeToast(
          "danger",
          "Impossibile accedere",
          "L'e-mail o la password non sono corretti"
        );
      }
    },
  },
};
</script>

<style scoped>
.login {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border-color: #017d91;
  box-shadow: 0 8px 6px -6px #60d69a;
}

form {
  text-align: left;
  align-items: center;
}

.btn {
  border-style: none;
  background: #017d91;
}

.btn:hover {
  background: #60d69a;
  transition: 0.5s all ease-in-out;
}
</style>
