<template>
  <div>
    <Card>
      <template v-slot:headerLeft> 列表 </template>
      <template v-slot:headerRight>
        
      </template>
      <template v-slot:content>
        <div class="content">
          <ExamTable examUrl="exam/getExams"></ExamTable>
        </div>
      </template>
    </Card>
  </div>
</template>

<script>
import Card from "@/components/Card.vue";
import ExamTable from "@/components/ExamTable.vue";
export default {
  data() {
    return {
    }
  },
  components: {
    Card,
    ExamTable
  },
};
</script>

<style scoped>
.content {
  padding: 10px;
}
</style>