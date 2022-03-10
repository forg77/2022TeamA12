<template>
  <div>
    <Card>
      <template v-slot:headerLeft> 题库列表</template>
      <template v-slot:headerRight>
        <table>
          <tr>
            <td style="font-size: 0">
              <SearchBox v-model:text="searchText" placeholder="按标题搜索"></SearchBox>
            </td>
            <td>
              <button class="btn" style="margin-left: 30px" @click="onSearchButtonClick()">{{ searchButtonString }}</button>
            </td>
          </tr>
        </table>
      </template>
      <template v-slot:content>
        <div class="content">
          <span style="margin-left:10px" v-show="extraData.search != null"
          >“{{ extraData.search }}”的搜索结果</span
          >
          <Table
              ref="table"
              :urls="urls"
              :columns="columns"
              :config="{ canClick: true }"
              @clickItem="clickItem"
              :extraData="extraData"
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
import {formatDate} from "@/common.ts";
import {getSearchInfo} from "@/composables/search.ts";

export default {
  data() {
    return {
      urls: {
        queryUrl: "question/getBanks",
      },
      columns: [
        {title: "题库名称", name: "name"},
        {title: "作者", name: "authorName"},
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
        {title: "题目数量", name: "count"},
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
  mounted() {
    this.searchCallback = () => {
      this.$refs.table.getItems();
    }
  },
  setup() {
    let info = getSearchInfo();
    info.searchButtonString.value = info.searchString.value = "搜索题库";
    return info;
  }
};
</script>

<style scoped>
.content {
  padding: 20.5px;
}
</style>
