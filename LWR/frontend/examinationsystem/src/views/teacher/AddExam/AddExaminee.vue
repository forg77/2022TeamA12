<template>
  <div>

  </div>
</template>

<script lang="ts" setup>

</script>

<script lang="ts">
import {defineComponent, PropType} from "vue";
import {ExamConfig} from "@/views/teacher/AddExam/ExamConfigSetup";

export default defineComponent({
  name: "Creation",
  data() {
    return {};
  },
  props: {
    examConfig: {
      type: Object as PropType<ExamConfig>,
      required: true
    },
  }
});
</script>

<style lang="scss" scoped>

</style>
