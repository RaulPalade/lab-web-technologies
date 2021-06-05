<template>
  <b-card
    :title="fullName"
    bg-variant="white"
    text-variant="black"
    id="teacher-card"
  >
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
#teacher-card {
  transition: 0.5s all ease-in-out;
}

h4.card-title {
  color: #017d91;
}

#course-card:hover {
  box-shadow: 0 8px 6px -6px #60d69a;
}
</style>
