<template>
  <div>
    <Exam :examId="examId"> </Exam>
  </div>
</template>

<script>
import Exam from "@/components/Exam.vue";
export default {
  data() {
    return {
      examId: null,
    };
  },
  components: {
    Exam,
  },
  created() {
    this.examId = this.$route.params.examId;
  },
};
</script>

<style scoped>
.content {
  padding: 20.5px;
}
</style>