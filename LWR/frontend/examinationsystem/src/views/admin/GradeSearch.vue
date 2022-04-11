<template>
  <div>
    <Card>
      <template v-slot:headerLeft>
        考试列表
      </template>
      <template v-slot:headerRight>
        <table>
          <tr>
            <td><DropDown /></td>
            <td style="font-size: 0"><SearchBox></SearchBox></td>
            <td>
              <button class="btn" style="margin-left: 10px">搜索考试</button>
            </td>
          </tr>
        </table>
      </template>
      <template v-slot:content>
        <div class="content">
          <Table :columns="columns"></Table>
        </div>
      </template>
    </Card>
  </div>
  <el-dialog width="50%" title="学生成绩" v-model='dialogvisible'>

    <el-table :data="gradedata" stripe >
      <el-table-column property="number" label="序号" />
      <el-table-column property="stuId" label="学号" />
      <el-table-column property="name" label="姓名" />
      <el-table-column property="score" label="成绩" />
    </el-table>

    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary">打印成绩</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import Card from "@/components/Card.vue";
import SearchBox from "@/components/SearchBox.vue";
import DropDown from "@/components/DropDown.vue";
import Table from "@/components/Table.vue";
export default {
  components: {
    Card,
    SearchBox,
    DropDown,
    Table,
  },
  data() {
    return {
      columns: [
        { title: "序号", name: "number" },
        { title: "考试名称", name: "examname" },
        {
          title: "负责教师",
          name: "teacher",
        },
        {
          title: "考试时间",
          name: "time",
        },
        { title: "考试状态", name: "state" },
        {
          title: "操作",
          name: "options",
        },
      ],
      gradedata:[
        {
          number:'1',
          stuId:"123213213",
          name:'小李',
          score:'100'
        },
        {
          number:'2',
          stuId:"123213213",
          name:'小李',
          score:'100'
        }
      ],
      dialogvisible: true
    };
  },
};
</script>
<style scoped>
.btn {
  outline: none;
}
.content {
  padding: 20px;
}
</style>