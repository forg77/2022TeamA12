<template>
  <div>
    <!-- 加载 -->
    <transition name="fade">
      <div class="loading-back" v-show="isLoading">
        <div style="width: 100%; height: 100%">
          <table style="width: 100%; height: 100%; vertical-align: middle">
            <td style="text-align: center">
              <Loading></Loading>
            </td>
          </table>
        </div>
      </div>
    </transition>
    <div class="question">
      <!-- <div class="score">
        <span>分数:</span>
        <input type="text" class="input-score" />
      </div> -->
      <div class="content">
        <table style="width: 100%;border-spacing: 0 10px">
          <tr>
            <td style="width: 50px;vertical-align: top">题干:</td>
            <td style="margin-left: 30px">
              <TextEdit v-model:content="question.description"/>
            </td>
          </tr>
        </table>
      </div>
    </div>
    <div class="line2"></div>
    <div class="anser">
      <div class="detail" style="display: flex">
        <div>选项:</div>
        <div style="color: red; margin-left: 30px">
          在当前页面选中的答案为本题正确答案
        </div>
      </div>
      <table style="margin-top: 20px;width: 100%;border-spacing: 0 10px">
        <template v-for="(choice, index) in question.choice" :key="index">
          <tr>
            <!--          <div-->
            <!--              class="anser-content"-->
            <!--              style="display: inline-block; vertical-align: middle"-->
            <!--          >-->
            <td style="width:50px">
              <input
                  type="radio"
                  name="rd"
                  :value="String.fromCharCode(65 + index)"
                  @click="
                question.answer = {};
                question.answer[index] = true;
              "
                  :checked="question.answer[index]"
              />{{ String.fromCharCode(65 + index) }}
            </td>
            <td>
              <TextEdit deleteString="删除选项" @deleteClick="question.choice.splice(index, 1)"
                        :deleteMenu="true" v-model:content="question.choice[index]"></TextEdit>
            </td>
            <!--          </div>-->

            <!--          <button class="BTN" @click="question.choice.splice(index, 1)">-->
            <!--            删除选项-->
            <!--          </button>-->
            <br/>
            <div style="height: 10px"></div>
          </tr>
        </template>
        <tr>
          <td></td>
          <td style="text-align: right;padding-top:10px">
            <span class="add" @click="question.choice.push('')">添加选项</span>
          </td>
        </tr>
      </table>
    </div>
    <div class="line2"></div>
    <div class="footer">
      <button
          class="btn2"
          style="background-color: #5399f3; color: #fff; border: none"
          @click="
          saveQuestion();
          $emit('save', question);
        "
      >
        保存该题
      </button>
      <button @click="reset()" class="btn2">重置</button>
    </div>
  </div>
</template>

<script>
import TextEdit from "@/components/TextEdit";
import axios from "axios";
import Loading from "@/components/Loading.vue";

export default {
  components: {
    TextEdit,
    Loading,
  },
  emits: ["save"],
  data() {
    return {
      question: this.getInitQuestion(),
      isLoading: false,
    };
  },
  created() {
    // if (this.id) this.question.id = this.id;
    // if (this.bankId) this.question.bankId = this.bankId;
    // console.log(this.id);
  },
  mounted() {
    if (this.questionBefore != null && this.questionBefore.type == "choice")
      this.question = this.questionBefore;
  },
  watch: {
    questionBefore: {
      handler(val) {
        if (val == null) {
          this.question = this.getInitQuestion();
        } else this.question = val;
      },
    },
  },
  methods: {
    saveQuestion() {
      let data = Object.assign({}, this.question);
      data.choice = JSON.stringify(data.choice);
      data.answer = JSON.stringify(data.answer);
      this.isLoading = true;
      axios({
        url: "question/addQuestion",
        data: data,
      })
          .then((res) => {
            if (res.data.errCode != 0) {
              alert("保存失败");
            }
            this.isLoading = false;
          })
          .catch(() => {
            alert("保存失败");
            this.isLoading = false;
          });
    },
    getInitQuestion() {
      let question = {
        description: "",
        type: "choice",
        choice: ["", "", "", ""],
        answer: {0: true},
      };
      if (this.questionBefore != null) {
        question.id = this.questionBefore.id;
        question.bankId = this.questionBefore.bankId;
      } else {
        question.id = this.id;
        question.bankId = this.bankId;
      }
      return question;
    },
    reset() {
      this.question = this.getInitQuestion();
    },
  },
  props: {
    id: {
      type: Number,
      default: null,
    },
    bankId: {
      type: Number,
      default: null,
    },
    questionBefore: {
      type: Object,
      default() {
        return null;
      },
    },
  },
};
</script>

<style scoped>
.loading-back {
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  background-color: #00000033;
  z-index: 30;
}
</style>
