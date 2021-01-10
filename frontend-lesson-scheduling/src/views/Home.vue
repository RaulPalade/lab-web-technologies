<template>
  <div class="home">
    <h2>Lesson Scheduling Home Page</h2>
    <CourseList />
  </div>
</template>

<script>
import CourseList from "@/components/CourseList";

export default {
  name: "Home",
  components: {
    CourseList,
  },
};
</script>

<style scoped>
.home {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
</style>
