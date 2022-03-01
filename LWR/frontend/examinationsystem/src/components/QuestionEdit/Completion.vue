<template>
  <div>
    <!-- 加载 -->
    <transition name="fade">
      <div class="loading-back" v-show="isLoading">
        <div style="width: 100%; height: 100%">
          <table style="width: 100%; height: 100%; vertical-align: middle">
            <td style="text-align: center"><Loading></Loading></td>
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
        <div>题干:</div>
        <div style="margin-left: 30px">
          <TextEdit v-model:content="question.description" />
        </div>
      </div>
    </div>
    <div class="line2"></div>
    <div class="anser">
      <div class="detail" style="display: flex">
        <div>答案:</div>
      </div>
      <div style="margin-top: 20px">
        <template v-for="(answer, index) in question.answerArray" :key="index">
          <div
            class="anser-content"
            style="display: inline-block; vertical-align: middle"
          >
            ({{ index + 1 }})
          </div>
          <div
            style="
              display: inline-block;
              vertical-align: middle;
              margin-left: 20px;
            "
          >
            <TextEdit v-model:content="question.answerArray[index]" />
          </div>
          <button class="BTN" @click="question.answerArray.splice(index, 1)">
            删除答案
          </button>
          <br />
          <div style="height: 10px"></div>
        </template>
        <button class="BTN" @click="question.answerArray.push('')">
          添加答案
        </button>
      </div>
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
import TextEdit from "@/components/TextEdit.vue";
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
    if (this.id) this.question.id = this.id;
    if (this.bankId) this.question.bankId = this.bankId;
    // console.log(this.id);
  },
  mounted() {
    if (
      this.questionBefore != null &&
      this.questionBefore.type == "completion"
    ) {
      this.question = this.questionBefore;
      this.question.answerArray = [];
      for (let index in this.question.answer) {
        this.question.answerArray[index] = this.question.answer[index];
      }
    }
  },
  watch: {
    questionBefore: {
      handler(val) {
        if (val == null) {
          this.question = this.getInitQuestion();
        } else {
          this.question = val;
          this.question.answerArray = [];
          for (let index in this.question.answer) {
            this.question.answerArray[index] = this.question.answer[index];
          }
        }
      },
    },
  },
  methods: {
    saveQuestion() {
      let data = Object.assign({}, this.question);
      data.answer = {};
      for (let index in data.answerArray) {
        data.answer[index] = data.answerArray[index];
      }
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
        type: "completion",
        answerArray: [""],
        answer: {},
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