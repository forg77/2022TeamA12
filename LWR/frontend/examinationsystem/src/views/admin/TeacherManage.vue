<template>
  <div>
    <Card>
      <template v-slot:headerLeft>
        教师列表
        <button class="btn" style="margin-left: 10px">添加教师</button>
        <button class="btn" style="margin-left: 10px">管理教师</button>
      </template>
      <template v-slot:headerRight>
        <table>
          <tr>
            <td><DropDown /></td>
            <td style="font-size: 0"><SearchBox></SearchBox></td>
            <td>
              <button class="btn" style="margin-left: 10px">搜索教师</button>
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
        {title: "专业",name: "subject"},
        {title: "手机号",name: "phonenumber"},
        {title: "教师工号",name: "teaID"},
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