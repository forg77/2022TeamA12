<template>
  <div>
    <Card>
      <template v-slot:headerLeft>
        学生列表
        <button class="btn" style="margin-left: 10px">添加学生</button>
        <button class="btn" style="margin-left: 10px">管理学生</button>
      </template>
      <template v-slot:headerRight>
        <table>
          <tr>
            <td><DropDown /></td>
            <td style="font-size: 0"><SearchBox></SearchBox></td>
            <td>
              <button class="btn" style="margin-left: 10px">搜索学生</button>
            </td>
          </tr>
        </table>
      </template>
      <template v-slot:content>
        <div class="content">
          <!-- <Table :columns="columns"></Table> -->
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
        { title: "姓名", name: "name" },
        { title: "学院", name: "colloge" },
        {
          title: "专业",
          name: "subject",
        },
        {
          title: "年级",
          name: "grade",
        },
        { title: "班级", name: "class" },
        {
          title: "学号",
          name: "stuID",
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