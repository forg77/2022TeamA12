<template>
  <div>
    <Card>
      <template v-slot:headerLeft>
        <span
          style="color: #ff3c3c; margin-right: 10px; cursor: pointer"
          @click="$router.back()"
          >返回</span
        >试题列表
      </template>
      <template v-slot:headerRight>
        <table>
          <tr>
            <td style="font-size: 0">
              <SearchBox placeholder="按标题搜索"></SearchBox>
            </td>
            <td>
              <button class="btn" style="margin-left: 30px">搜索题目</button>
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
        queryUrl: "question/getQuestions",
      },
      columns: [
        { title: "题目描述", name: "description" },
        {
          title: "试题类型",
          name: "type",
          transformer(value) {
            if (value == "choice") return "选择题";
            else if(value == "multi_choice") return "多选题";
            else if(value == "completion") return "填空题";
            else if(value == "short_answer") return "简答题";
          },
        },
        { title: "所属题库", name: "bankName" },
      ],
    };
  },
  methods: {},
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