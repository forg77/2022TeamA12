<template>
  <div>
    <Card>
      <template v-slot:headerLeft> 题库列表 </template>
      <template v-slot:headerRight>
        <table>
          <tr>
            <td style="font-size: 0">
              <SearchBox placeholder="按标题搜索"></SearchBox>
            </td>
            <td>
              <button class="btn" style="margin-left: 30px">搜索题库</button>
            </td>
          </tr>
        </table>
      </template>
      <template v-slot:content>
        <div class="content">
          <Table
            :urls="urls"
            :columns="columns"
            :config="{ canClick: true }"
            @clickItem="clickItem"
          ></Table>
        </div>
      </template>
    </Card>
  </div>
</template>

<script>
import Card from "@/components/Card.vue";
import SearchBox from "@/components/SearchBox.vue";
import Table from "@/components/Table.vue";
import { formatDate } from "@/common.js";
export default {
  data() {
    return {
      urls: {
        queryUrl: "question/getBanks",
      },
      columns: [
        { title: "题库名称", name: "name" },
        { title: "作者", name: "authorName" },
        {
          title: "创建时间",
          name: "creationTime",
          transformer(value) {
            return formatDate(new Date(value));
          },
        },
        {
          title: "私有",
          name: "private",
          transformer(value) {
            return value ? "是" : "否";
          },
        },
        { title: "题目数量", name: "count" },
      ],
    };
  },
  methods: {
    clickItem(item) {
      this.$router.push("/teacher/questionManage/" + item.id);
    },
  },
  components: {
    Card,
    SearchBox,
    Table,
  },
};
</script>

<style scoped>
.content {
  padding: 20.5px;
}
</style>