<template>
  <div class="parent" style="text-align: center">
    <!--    <Table :urls="{queryUrl:'test/test'}" :columns="columns"></Table>-->
    <!--    <div style="width: 190px; margin:35px auto 0 auto">-->
    <!--      <el-button type="primary" size="large">编辑考题</el-button>-->
    <!--      <el-button size="large">取消</el-button>-->
    <!--    </div>-->
    <el-transfer
        v-model="value"
        style="text-align: left; display: inline-block"
        filterable
        :titles="['所有学生', '考生']"
        :format="{
        noChecked: '${total}',
        hasChecked: '${checked}/${total}',
      }"
        :data="data"
    >
    </el-transfer>
  </div>
</template>

<script lang="ts" setup>
import Table from "@/components/Table";
</script>

<script lang="ts">
import {defineComponent, PropType} from "vue";
import {ExamForm} from "@/views/teacher/AddExam/ExamConfigSetup";
import {Response} from "@/models";
import axios from "axios";

export default defineComponent({
  name: "Creation",
  data() {
    return {
      data: [{
        key: 1,
        label: `123`,
      }],
      value: []
      // columns: [
      //   {title: "id", name: "id"},
      //   {title: "标题1", name: "a"},
      //   {title: "标题2", name: "b"},
      //   {title: "标题3", name: "c"},
      // ],
    };
  },
  methods: {
    getData() {
      if (this.user == null)
        return;
      this.data = [];
      this.$store.state.config.showLoading = true;
      return axios({
        url: '/user/getTeacherStudents',
        data: {
          teacherId: this.user.id
        }
      }).then((res) => {
        const data: Response = res.data;
        const students = data.data.data;
        for (const student of students) {
          this.data.push({
            key: student.id,
            label: `${student.realName}|${student.college}|${student.number}`
          });
        }
      }).catch(() => {

      }).finally(() => {
        this.$store.state.config.showLoading = false;
      });
    }
  },
  created() {
    this.getData();
  },
  props: {
    form: {
      type: Object as PropType<ExamForm>,
      required: true
    },
  },
  computed: {
    user() {
      return this.$store.state.config.user;
    }
  },
  watch: {
    user() {
      this.getData();
    }
  }
});
</script>

<style lang="scss" scoped>
.parent :deep(.el-transfer-panel) {
  width: 350px;
  height: 500px;
}

.parent :deep(.el-transfer-panel__body) {
  height: 100%;
}

.parent :deep(.el-transfer-panel__filter) {
  width: 90%;
}
</style>
