<template>
  <div>
    <Card>
      <template v-slot:headerLeft> 成绩列表 </template>
      <template v-slot:headerRight>
        <SearchBox v-model:text="searchText" placeholder="考试名称"></SearchBox>
        <button
          @click="onSearchButtonClick()"
          class="btn"
          style="margin-left: 30px"
        >
          {{ searchButtonString }}
        </button>
      </template>
      <template v-slot:content>
        <div class="content">
          <span style="margin-left: 10px" v-show="extraData.search != null"
            >“{{ extraData.search }}”的搜索结果</span
          >
          <Table
            ref="table"
            :urls="urls"
            :columns="columns"
            :extraData="extraData"
            :config="tableConfig"
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
      searchText: "",
      searchButtonString: "搜索考试",
      extraData: {
        examinee: null,
      },
      tableConfig: {
        canSelect: false,
        canMultiSelect: true,
        canClick: false,
        getItemsOnCreate: false,
      },
      urls: {
        queryUrl: "exam/getGrades",
      },
      columns: [
        { title: "考试名称", name: "title" },
        {
          title: "考试时间",
          name: "startTime",
          transformer(val) {
            return formatDate(new Date(val));
          },
        },
        { title: "考试次数", name: "times" },
        { title: "总分", name: "fullMark" },
        {
          title: "成绩",
          name: "grade",
          transformer(val) {
            if (!val) return "暂无成绩";
            return val;
          },
        },
      ],
      config: {},
    };
  },
  methods: {
    onSearchButtonClick() {
      if (this.extraData.search == null) {
        if (this.searchText != "") {
          this.extraData.search = this.searchText;
          this.searchButtonString = "取消搜索";
          this.$refs.table.getItems();
        }
      } else {
        this.extraData.search = null;
        this.searchButtonString = "搜索考试";
        this.$refs.table.getItems();
      }
    },
  },
  computed: {
    user() {
      return this.$store.state.config.user;
    },
  },
  watch: {
    user(newVal) {
      if (newVal != null) {
        this.extraData.examinee = newVal.id;
        this.$refs.table.getItems();
      }
    },
    searchText(newVal) {
      if (newVal == "" && this.extraData.search != null) {
        this.extraData.search = null;
        this.searchButtonString = "搜索考试";
        this.$refs.table.getItems();
      }
    },
  },
  mounted() {
    if (this.user) {
      this.extraData.examinee = this.user.id;
      this.$refs.table.getItems();
    }
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