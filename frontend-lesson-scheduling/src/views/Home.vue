<template>
  <div class="home mb-3">
    <b-card
      :img-src="introImage"
      img-alt="Intro image"
      img-left
      class="mb-3"
      id="showcase"
    >
      <b-card-text id="showcase-text">
        <span class="first-row">Raggiungi i tuoi obiettivi </span>
        <span class="second-row">Esplora i nostri corsi e docenti</span>
      </b-card-text>
    </b-card>

    <div class="mt-5">
      <h3>CORSI DISPONIBILI</h3>
      <CourseList />
      <h3>DOCENTI DISPONIBILI</h3>
      <TeacherList />
    </div>
  </div>
</template>

<script>
import CourseList from "@/components/CourseList";
import TeacherList from "@/components/TeacherList";
import introImage from "@/assets/intro/3718985.jpg";

export default {
  name: "Home",
  components: {
    CourseList,
    TeacherList,
  },
  data() {
    return { introImage };
  },
};
</script>

<style scoped>
.home {
  margin: 5rem;
  padding: 0;
  box-sizing: border-box;
}

#showcase {
  align-items: center;
  border-style: none;
}

#showcase-text {
  display: flex;
  flex-direction: column;
  text-align: left;
  text-transform: uppercase;
  font-weight: 900;
  font-size: 3rem;
  text-decoration-color: #017d91;
}

#showcase-text .first-row {
  color: #017d91;
}

#showcase-text .second-row {
  color: #60d69a;
}

img {
  max-width: 40%;
}

h3 {
  font-weight: 900;
  text-align: left;
  font-size: 2rem;
  text-decoration: underline;
  text-decoration-color: #017d91;
  padding: 1rem;
}
</style>
