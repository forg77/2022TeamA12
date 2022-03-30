<template>
  <Card style="margin-right: auto" :headerHeight="64">
    <template v-slot:headerLeft>
      异常列表
      <button class="alert" style="width: 80px">提醒</button></template
    >
    <template v-slot:headerRight>
      <Dropdown></Dropdown>
      &nbsp;
      <Search></Search>
      &nbsp;
      <button class="search">搜索考试</button>
    </template>
    <template v-slot:content>
      <table
        style="
          border-collapse: separate;
          border-spacing: 40px 10px;
          width: 100%;
        "
        frame="below"
      >
        <tr>
          <th>序号</th>
          <th>姓名</th>
          <th>学院</th>
          <th>学号</th>
          <th>异常时间</th>
          <th>考场座位</th>
        </tr>
        <tr>
          <td><input type="checkbox" />1</td>
          <td>张三</td>
          <td>信息学院</td>
          <td>2019329621267</td>
          <td>2020-01-25</td>
          <td>10-111</td>
        </tr>
      </table>
    </template>
  </Card>
</template>

<script lang="ts">
import Card from "@/components/Card.vue";
import Search from "@/components/SearchBox.vue";
import Dropdown from "@/components/DropDown.vue";
import { defineComponent } from "vue";
import AccountManage from "@/views/Personal/AccountManage.vue";
import BasicInfo from "@/views/Personal/BasicInfo.vue";

export default defineComponent({
  components: { Card, BasicInfo, AccountManage, Search, Dropdown },
  data() {
    return {
      content: "AccountManage",
    };
  },
});
</script>
<style lang="scss" scoped>
.dialog {
  width: 800px;
  height: 683px;
  background: #ffffff;
  border: 1px solid #707070;
  opacity: 1;
  border-radius: 0px;
}
.search,
.alert {
  width: 100px;
  height: 30px;
  background: #338afb;
  opacity: 1;
  border-radius: 5px;
  border: none;
  color: white;
  outline: none;
}
td {
  text-align: center;
}
</style>
