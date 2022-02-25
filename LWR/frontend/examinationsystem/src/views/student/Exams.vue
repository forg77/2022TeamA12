<template>
  <div>
    <Card>
      <template v-slot:headerLeft>
        <span>考试列表</span>
      </template>
      <template v-slot:headerRight>
        <DropDown :values="dropDown" @change="onDropDownChange"></DropDown
        ><span style="margin: 0 10px"></span>
        <SearchBox v-model:text="searchText" placeholder="考试名称"></SearchBox>
        <button
          class="btn"
          style="vertical-align: middle; margin-left: 18px"
          @click="onSearchButtonClick()"
        >
          {{ searchButtonString }}
        </button>
      </template>
      <template v-slot:content>
        <div class="content">
          <span style="margin-left:10px" v-show="extraData.search != null"
            >“{{ extraData.search }}”的搜索结果</span
          >
          <ExamTable
            ref="examTable"
            examUrl="exam/getExams"
            @cardClick="onCardClick"
            :extraData="extraData"
          ></ExamTable>
        </div>
      </template>
    </Card>
  </div>
</template>

<script>
import Card from "@/components/Card.vue";
import ExamTable from "@/components/ExamTable.vue";
import SearchBox from "@/components/SearchBox.vue";
import DropDown from "@/components/DropDown.vue";
export default {
  data() {
    return {
      // search: null,
      searchText: "",
      searchButtonString: "搜索考试",
      extraData: {},
      dropDown: [
        { value: "all", text: "全部" },
        { value: "notStarted", text: "未开始" },
        { value: "going", text: "进行中" },
        { value: "over", text: "已结束" },
      ],
    };
  },
  methods: {
    onCardClick(exam) {
      this.$router.push("/student/studentExam/" + exam.id);
    },
    onSearchButtonClick() {
      if (this.extraData.search == null) {
        if (this.searchText != "") {
          this.extraData.search = this.searchText;
          this.searchButtonString = "取消搜索";
          this.$refs.examTable.getExams();
        }
      } else {
        this.extraData.search = null;
        this.searchButtonString = "搜索考试";
        this.$refs.examTable.getExams();
      }
    },
    onDropDownChange(val) {
      // console.log({val});
      this.extraData.screen = val;
      this.$refs.examTable.getExams();
    },
  },
  watch: {
    searchText(newVal) {
      if (newVal == "" && this.extraData.search != null) {
        this.extraData.search = null;
        this.searchButtonString = "搜索考试";
        this.$refs.examTable.getExams();
      }
    },
  },
  components: {
    Card,
    ExamTable,
    SearchBox,
    DropDown,
  },
};
</script>

<style scoped>
.content {
  padding: 10px;
}
</style>