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