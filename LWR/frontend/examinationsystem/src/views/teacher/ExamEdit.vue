<template>
  <div>
    <ExamEdit :examId="examId"> </ExamEdit>
  </div>
</template>

<script>
import ExamEdit from "@/components/ExamEdit.vue";
export default {
  data() {
    return {
      examId: null,
    };
  },
  components: {
    ExamEdit,
  },
  created() {
    this.examId = Number(this.$route.params.examId);
  },
};
</script>

<style scoped>
.content {
  padding: 20.5px;
}
</style>