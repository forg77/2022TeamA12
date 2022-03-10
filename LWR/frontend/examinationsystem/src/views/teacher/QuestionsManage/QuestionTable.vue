<template>
  <div>
    <transition name="fade">
      <div
          class="loading-back"
          v-show="showQuestionEdit"
          @click="showQuestionEdit = false"
      >
        <div style="width: 100%; height: 100%">
          <table style="width: 100%; height: 100%; vertical-align: middle">
            <td>
              <div
                  @click="$event.stopPropagation()"
                  style=" height:600px;margin:auto;width:1000px"
              >
                <QuestionEdit
                    :id="selectId"
                    :bankId="extraData.bankId"
                    @save="$refs.table.getItems()"
                    :key="editKey"
                ></QuestionEdit>
              </div>
            </td>
          </table>
        </div>
      </div>
    </transition>
    <Card>
      <template v-slot:headerLeft>
        <span
            style="color: #338AFB; margin-right: 10px; cursor: pointer"
            @click="$router.replace('/teacher/questionsManage')"
        >返回</span
        >试题列表
        <button class="btn" style="margin-left: 20px" @click="onAddClick()">
          添加试题
        </button>
      </template>
      <template v-slot:headerRight>
        <table>
          <tr>
            <td style="font-size: 0">
              <SearchBox v-model:text="searchText" placeholder="按标题搜索"></SearchBox>
            </td>
            <td>
              <button class="btn" style="margin-left: 30px" @click="onSearchButtonClick()">{{
                  searchButtonString
                }}
              </button>
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
              @clickItem="clickItem"
              :urls="urls"
              :columns="columns"
              :config="tableConfig"
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
import QuestionEdit from "@/components/QuestionEdit";
import {formatDate} from "@/common.ts";
import {getSearchInfo} from "@/composables/search.ts";

export default {
  data() {
    return {
      showQuestionEdit: false,
      selectId: null,
      editKey: 0,
      // bankId: null,
      // extraData: {
      //   bankId: null,
      // },
      urls: {
        queryUrl: "question/getQuestions",
      },
      tableConfig: {
        canSelect: false,
        canMultiSelect: true,
        canClick: true,
        getItemsOnCreate: false,
      },
      columns: [
        {
          title: "题目描述",
          name: "description",
          transformer(value) {
            const regex = /<\/?.+?\/?>/gm;
            return value.replace(regex, "").replace(/&nbsp;/gi, "");
          },
        },
        {
          title: "试题类型",
          name: "type",
          transformer(value) {
            if (value == "choice") return "选择题";
            else if (value == "multi_choice") return "多选题";
            else if (value == "completion") return "填空题";
            else if (value == "short_answer") return "简答题";
          },
        },
        {title: "所属题库", name: "bankName"},
      ],
    };
  },
  methods: {
    clickItem(item) {
      this.selectId = item.id;
      this.showQuestionEdit = true;
      this.editKey++;
    },
    onAddClick() {
      this.selectId = null;
      this.showQuestionEdit = true;
      this.editKey++;
    },
  },
  components: {
    Card,
    SearchBox,
    Table,
    QuestionEdit,
  },
  mounted() {
    this.extraData.bankId = Number(this.$route.params.bankId);
    this.$refs.table.getItems();

    this.searchCallback = () => {
      this.$refs.table.getItems();
    }
    // this.searchString = "搜索题库";
    // this.extraData.bankId = null;
  },
  setup() {
    let info = getSearchInfo();
    info.searchButtonString.value = info.searchString.value = "搜索题目";
    info.extraData.value.bankId = null;
    return info;
  }
};
</script>

<style scoped>
.content {
  padding: 20.5px;
}

.loading-back {
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  background-color: #00000033;
  z-index: 20;
}
</style>
