<template>
  <div class="table">
    <!--确认删除-->
    <DialogBox v-model:show="showDeleteDialog">
      <template v-slot:header>确认</template>
      确定删除所选项吗？
      <template v-slot:bottom>
        <button
            class="btn"
            @click="
            deleteExam(selectExam.id);
            showDeleteDialog = false;
          "
            style="margin-right: 20px"
        >
          确定
        </button>
        <button class="btn" @click="showDeleteDialog = false">取消</button>
      </template>
    </DialogBox>
    <table style="width: 100%">
      <td style="text-align: center">
        <Loading v-show="isLoading"></Loading>
      </td>
    </table>
    <ClickDropDown id="menu" :expand="expandMenu" :position="menuPos" :items="editMenu"
                   @itemClick="onMenuItemClick"></ClickDropDown>
    <template v-for="exam in exams" :key="exam.id">
      <ExamCard :tag="getExamTag(exam)" @click="this.$emit('cardClick', exam)" :canManage="canManage"
                @manageClick="selectExam=exam;onManageClick($event,exam);">
        <template v-slot:title>{{ exam.title }}</template>
        <template v-slot:subtitle>{{ exam.subtitle }}</template>
        <template v-slot:time>{{
            formatDate(new Date(exam.earliestStartTime))
          }}
        </template>
        <template v-slot:limitTime
        >限时{{ Math.floor(exam.duration / 1000 / 60) }}分钟
        </template
        >
        <template v-slot:score>满分{{ exam.fullMark }}</template>
      </ExamCard>
    </template>
    <ExamCardAdd v-show="canAdd && !isLoading" @click="$emit('addClick')">

    </ExamCardAdd>

    <table class="footer">
      <tr>
        <td style="width: 44%; text-align: right">
          <span class="normal-text"
          >共{{ getTotalPages() }}页，共{{ totalCount }}条</span
          >

          <input
              id="setItemsPerPage"
              style="margin-left: 30px"
              class="inputBox input"
              type="number"
              :value="itemsPerPage"
              @input="onItemsPerPageChanged($event)"
          />
          <span class="normal-text" style="margin-right: 30px">条/页</span>
        </td>
        <td style="text-align: center; font-size: 0">
          <table class="page">
            <tr>
              <td
                  class="active"
                  :class="{ disable: currentPage <= 1 }"
                  @click="gotoPage(currentPage - 1)"
              >
                <svg-icon iconName="left" className="arrow"></svg-icon>
              </td>
              <td>
                <span class="text">{{ currentPage }}</span>
              </td>
              <td
                  class="active"
                  :class="{
                  disable: currentPage >= getTotalPages(),
                }"
                  @click="gotoPage(currentPage + 1)"
              >
                <svg-icon iconName="right" className="arrow"></svg-icon>
              </td>
            </tr>
          </table>
        </td>
        <td style="width: 44%; text-align: center">
          <span class="normal-text">前往</span
          ><input
            id="setPage"
            class="inputBox input"
            type="number"
            @input="onCurrentPageChanged($event)"
            :value="currentPage"
            style="-moz-appearance: textfield"
        /><span class="normal-text">页</span>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
import ExamCard from "./ExamCard.vue";
import ExamCardAdd from "./ExamCardAdd.vue"
import Loading from "./Loading.vue";
import ClickDropDown from "@/components/ClickDropDown";
import axios from "axios";
import {formatDate} from "@/common.ts";
import DialogBox from "@/components/DialogBox";

export default {
  components: {ExamCard, Loading, ExamCardAdd, ClickDropDown, DialogBox},
  data() {
    return {
      exams: [],
      totalCount: 0,
      order: null,
      desc: false,
      itemsPerPage: 6,
      currentPage: 1,
      ajaxCancel: null,

      formatDate: formatDate,
      timeNow: new Date(),

      isLoading: false,

      menuPos: {
        x: 300,
        y: 300
      },
      expandMenu: false,

      editMenu: [{key: "delete", value: "删除"}, {key: "correct", value: "批改"}],
      selectExam: null,

      showDeleteDialog: false
    };
  },
  emits: ["cardClick", "addClick"],
  methods: {
    getExams() {
      if (this.ajaxCancel != null) {
        this.ajaxCancel();
        this.ajaxCancel = null;
      }
      this.exams = [];
      this.isLoading = true;

      let data = {
        offset: this.itemsPerPage * (this.currentPage - 1),
        max: this.itemsPerPage,
        order: this.order,
        desc: this.desc,
      };
      for (let pro in this.extraData) {
        data[pro] = this.extraData[pro];
      }
      axios({
        url: this.examUrl,
        cancelToken: new axios.CancelToken((c) => {
          this.ajaxCancel = c;
        }),
        data: data,
      }).then((res) => {
        this.isLoading = false;
        this.exams = res.data.data.data;
        this.totalCount = res.data.data.count;
      });
    },
    getTotalPages() {
      let totalPages = Math.floor(this.totalCount / this.itemsPerPage);
      if (this.totalCount % this.itemsPerPage != 0) totalPages++;
      return totalPages;
    },
    gotoPage(page) {
      let newPage = this.currentPage;

      if (page <= 0) newPage = 1;
      else {
        let totalPages = this.getTotalPages();
        if (totalPages == 0) newPage = 1;
        else if (page > totalPages) newPage = totalPages;
        else newPage = page;
      }

      if (newPage != this.currentPage) {
        this.currentPage = newPage;
        this.getExams();
      }
    },
    onCurrentPageChanged(event) {
      let target = event.target;

      if (target.value <= 0) target.value = 1;
      else {
        let totalPages = this.getTotalPages();
        if (totalPages == 0) target.value = 1;
        else if (target.value > totalPages) target.value = totalPages;
      }
      if (target.value != this.currentPage) {
        this.currentPage = target.value;
        this.getExams();
      }
    },
    onItemsPerPageChanged(event) {
      let target = event.target;

      if (target.value <= 0) target.value = 1;
      else if (target.value > 100) target.value = 100;
      if (target.value != this.itemsPerPage) {
        this.itemsPerPage = target.value;
        let totalPages = this.getTotalPages();
        if (this.currentPage > totalPages) this.currentPage = totalPages;
        this.getExams();
      }
    },
    isExamOver(exam) {
      return this.timeNow > new Date(exam.latestStartTime + exam.duration);
    },
    isExamGoing(exam) {
      return (
          this.timeNow <= new Date(exam.latestStartTime + exam.duration) &&
          this.timeNow >= new Date(exam.earliestStartTime)
      );
    },
    getExamTag(exam) {
      if (this.isExamOver(exam)) return "已结束";
      else if (this.isExamGoing(exam)) return "进行中";
      else return "";
    },
    onManageClick(event, exam) {
      // console.log(event)
      // let target = event.target;
      // let x = target.getBoundingClientRect().left + document.documentElement.scrollLeft;
      // let y = target.getBoundingClientRect().top + document.documentElement.scrollTop;
      this.menuPos.x = event.pageX;
      this.menuPos.y = event.pageY;
      this.expandMenu = true;
    },
    deleteExam(id) {
      axios({
        url: "exam/deleteExam",
        data: {
          id: id
        }
      }).then((res) => {
        if (res.data["errCode"] !== 0) {
          alert("删除失败");
        } else {
          this.getExams();
        }
      }).catch(() => {
        alert("删除失败");
      });

    },
    onMenuItemClick(key) {
      // console.log(1);
      if (key === 'delete') {
        // this.deleteExam(this.selectExam["id"]);
        this.showDeleteDialog = true;
      } else if (key === 'correct') {
        this.$router.push('/teacher/examCorrect/' + this.selectExam.id);
      }
    }
  },
  mounted() {
    this.timeNow = new Date();
    this.getExams();

    // let menu = document.querySelector('#menu')
    let self = this;
    document.addEventListener("click", function (e) {
      // 判断被点击的元素是不是scheduleInput元素，不是的话，就隐藏之
      // if (e.target !== menu) {
      self.expandMenu = false;
      // }
    });
  },
  props: {
    examUrl: {
      type: String,
      default: "",
    },
    extraData: {
      type: Object,
      default() {
        return {};
      },
    },
    canAdd: {
      type: Boolean,
      default: false,
    },
    canManage: {
      type: Boolean,
      default: false,
    }
    // editMenu: {
    //   type: Array,
    //   default() {
    //     return [];
    //   }
    // }
  },
  computed: {},
};
</script>

<style scoped>
.table {
  min-height: 200px;
}

.footer {
  width: 100%;
  margin-top: 35px;
}

.footer .normal-text {
  font-size: 16px;
  font-family: Microsoft YaHei;
  font-weight: 400;

  opacity: 0.7;
}

.page {
  display: inline-block;
}

.page .text {
  font-size: 16px;
  font-family: Microsoft YaHei;
  color: #338afb;
  opacity: 0.7;
  margin: 0 20px;
}

.page .arrow {
  width: 18px;
  height: 18px;
}

.page .active {
  cursor: pointer;
}

.page .disable {
  opacity: 0.2;
  cursor: default;
}

.input {
  width: 59px;
  height: 28px;
  margin: 0 4px;
  text-align: center;

  font-size: 16px;
  font-family: Microsoft YaHei;
  font-weight: 400;
  line-height: 0px;

  color: #010101b2;
  border-radius: 5px;
}
</style>
