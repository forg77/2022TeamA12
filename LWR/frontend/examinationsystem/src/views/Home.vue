<template>
  <div>
    <ReadOver :examId="examId"> </ReadOver>
  </div>
</template>

<script>
import ReadOver from "@/components/ReadOver.vue";
export default {
  data() {
    return {
      examId: null,
    };
  },
  components: {
    ReadOver,
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