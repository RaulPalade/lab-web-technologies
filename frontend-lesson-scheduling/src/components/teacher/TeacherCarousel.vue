<template>
  <div class="cards">
    <b-container class="mt-3 d-inline align-items-stretch">
      <b-row>
        <b-col>
          <carousel autoplay :paginationSize="12" :perPage="4">
            <slide
              v-for="teacher in teachers"
              :teacher="teacher"
              :key="teacher.id"
              class="p-2"
            >
              <TeacherCard :teacher="teacher" />
            </slide>
          </carousel>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import TeacherCard from "./TeacherCard";
import { mapGetters, mapActions } from "vuex";

export default {
  name: "TeacherCarousel",
  components: {
    TeacherCard,
  },
  created: function() {
    this.GetActiveTeachers();
  },
  computed: {
    ...mapGetters({ teachers: "StateActiveTeachers", User: "StateUser" }),
  },
  methods: {
    ...mapActions(["GetActiveTeachers"]),
  },
};
</script>

<style></style>
