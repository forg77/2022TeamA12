<template>
  <div>
    <Table :urls="{queryUrl:'test/test'}" :columns="columns"></Table>
<!--    <div style="width: 190px; margin:35px auto 0 auto">-->
<!--      <el-button type="primary" size="large">编辑考题</el-button>-->
<!--      <el-button size="large">取消</el-button>-->
<!--    </div>-->
  </div>
</template>

<script lang="ts" setup>
import Table from "@/components/Table";
</script>

<script lang="ts">
import {defineComponent, PropType} from "vue";
import {ExamForm} from "@/views/teacher/AddExam/ExamConfigSetup";

export default defineComponent({
  name: "Creation",
  data() {
    return {
      columns: [
        {title: "id", name: "id"},
        {title: "标题1", name: "a"},
        {title: "标题2", name: "b"},
        {title: "标题3", name: "c"},
      ],
    };
  },
  props: {
    form: {
      type: Object as PropType<ExamForm>,
      required: true
    },
  }
});
</script>

<style lang="scss" scoped>

</style>
