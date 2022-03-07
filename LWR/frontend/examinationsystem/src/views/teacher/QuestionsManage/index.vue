<template>
  <component :is="page"></component>
</template>

<script>
import QuestionsTable from "./QuestionsTable.vue"

export default {
  data() {
    return {
      page:"QuestionsTable"
    };
  },
  methods:{

  },
  components: {
    QuestionsTable,
  },
};
</script>

<style scoped>
.content {
  padding: 20.5px;
}
</style>