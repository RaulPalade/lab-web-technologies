<template>
  <b-card
    :title="course.title"
    img-src="https://placekitten.com/g/400/450"
    img-alt="Image"
    img-top
  >
  </b-card>
</template>
<script>
export default {
  props: ["course"],
};
</script>
<style scoped>
</style>