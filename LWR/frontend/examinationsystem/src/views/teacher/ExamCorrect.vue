<template>
  <div>
    <ReadOver></ReadOver>
  </div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import ReadOver from "@/components/ReadOver.vue";

export default defineComponent({
  data() {
    return {
      examId: null,
    };
  },
  components: {
    ReadOver
  },
});
</script>

<style scoped>

</style>
