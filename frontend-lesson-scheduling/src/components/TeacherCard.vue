<template>
  <b-card :title="fullName" bg-variant="dark" text-variant="black">
    <b-button id="btn">Prenota</b-button>
  </b-card>
</template>

<script>
export default {
  props: ["teacher"],
  data() {
    return {
      fullName: this.teacher.name + " " + this.teacher.surname,
    };
  },
};
</script>

<style>
</style>