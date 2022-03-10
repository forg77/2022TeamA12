<template>
  <!-- 加载 -->
  <!-- <transition name="fade">
    <div class="loading-back" v-show="isLoading">
      <div style="width: 100%; height: 100%">
        <table style="width: 100%; height: 100%; vertical-align: middle">
          <td style="text-align: center"><Loading></Loading></td>
        </table>
      </div>
    </div>
  </transition> -->
  <table
    class="exam"
    cellspacing="0"
    cellpadding="0"
    style="width: calc(100% - 150px); max-width: 1300px"
  >
    <tr>
      <td style="width: 262px">
        <table cellspacing="0" cellpadding="0">
          <tr>
            <td>
              <div class="card" style="height: 84px; margin-top: 0">
                <table style="margin: auto; margin-top: 15px">
                  <tr>
                    <td style="font-weight: bold">本题满分:</td>
                    <td><span style="color: blue">10</span></td>
                    <td style="font-weight: bold">分</td>
                    <td style="font-weight: bold">自动阅卷</td>
                  </tr>
                  <tr>
                    <td style="font-weight: bold">给分间隔:</td>
                    <td>
                      <span style="color: blue">0.5</span>
                    </td>
                    <td style="font-weight: bold">分</td>
                    <td>
                      <input type="checkbox" class="switch" />
                    </td>
                  </tr>
                </table>
              </div>
            </td>
          </tr>
          <tr>
            <td>
              <div
                class="card"
                style="width: 262px; height: 506px; padding: 20px"
              >
                <div style="font-weight: bold">题目列表:</div>
                <br />
                <div>
                  <div style="font-size: 12px">
                    1.衬衫的价格是不是九磅十五……
                  </div>
                  <table>
                    <tr>
                      <td>
                        <!-- 进度条 -->
                        <div
                          style="
                            height: 3px;
                            width: 150px;
                            background-color: rgb(199, 199, 199);
                            border-radius: 5px;
                            margin-right: 20px;
                          "
                        >
                          <div
                            style="
                              height: 3px;
                              width: 50%;
                              background-color: #338afb;
                              border-radius: 5px;
                            "
                          ></div>
                        </div>
                      </td>
                      <td><span style="font-size: 12px">50%</span></td>
                    </tr>
                  </table>
                </div>
              </div>
            </td>
          </tr>
        </table>
      </td>
      <td style="min-width: 700px">
        <table cellspacing="0" cellpadding="0" style="width: 100%">
          <tr>
            <td>
              <div
                class="card"
                style="width: 100%; height: 84px; margin-top: 0"
              >
                <table
                  cellpadding="0"
                  cellspacing="0"
                  style="height: 100%; width: 100%; color: black; padding: 14px"
                >
                  <tr>
                    <td style="vertical-align: top">
                      <table
                        cellpadding="0"
                        cellspacing="0"
                        style="width: 100%"
                      >
                        <tr>
                          <td>
                            <table cellpadding="0" cellspacing="0">
                              <tr>
                                <td
                                  style="
                                    font-weight: bold;
                                    font-size: 21px;
                                    padding-right: 28px;
                                  "
                                >
                                  {{ exam.title }}
                                </td>
                                <td
                                  style="
                                    font-size: 14px;
                                    opacity: 0.7;
                                    vertical-align: bottom;
                                  "
                                >
                                  {{ exam.subtitle }}
                                </td>
                              </tr>
                            </table>
                          </td>
                          <td style="text-align: right; font-size: 21px">
                            <span style="font-weight: bold">批阅人：</span
                            >{{ user && user.nickname }}
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                  <tr>
                    <td style="vertical-align: bottom">
                      <table
                        cellpadding="0"
                        cellspacing="0"
                        style="width: 100%; font-size: 14px"
                      >
                        <tr>
                          <td>
                            个人任务：<span
                              class="val-text"
                              style="margin-right: 28px"
                              >{{ exam.questionsCount }}</span
                            >
                            个人平均分：<span
                              class="val-text"
                              style="margin-right: 28px"
                              >{{ exam.fullMark }}</span
                            >
                            上一份耗时：<span class="val-text">{{
                              stopTimeString
                            }}</span>
                          </td>
                          <td style="text-align: right">
                            <svg-icon
                              iconName="sandglass"
                              className="sandglass"
                            ></svg-icon>
                            <span style="vertical-align: middle">
                              总耗时：<span class="val-text">{{
                                Math.floor(remainingTime / 1000 / 60)
                              }}</span
                              >分钟<span class="val-text">{{
                                Math.floor((remainingTime / 1000) % 60)
                              }}</span
                              >秒
                            </span>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </table>
              </div>
            </td>
          </tr>
          <tr>
            <td>
              <div
                class="exam-content card"
                style="width: 100%; height: 426px; display: flex"
              >
                <div style="height: 100%; width: 45%; padding: 20px">
                  <div style="height: 50%; width: 100%">当前题目:</div>
                  <div style="height: 50%; width: 100%">参考答案:</div>
                </div>
                <div
                  class="line"
                  style="height: 100%; border: solid 0.5px"
                ></div>
                <div style="height: 100%; width: 55%">
                  <div style="width: 100%; height: 70%; padding: 20px">
                    考生答案:
                  </div>
                  <div
                    class="line"
                    style="width: 100%; border: solid 0.5px; height: 0.05%"
                  ></div>
                  <div
                    style="
                      width: 100%;
                      height: 29.8%;
                      padding: 20px;
                      display: flex;
                    "
                  >
                    <div>
                      <div>
                        分值:
                        <input
                          type="text"
                          placeholder="请输入分值"
                          style="
                            width: 95px;
                            height: 30px;
                            border-radius: 5px;
                            border: solid 0.8px;
                          "
                        />
                      </div>
                      <br />
                      <div>
                        <button class="score">满分</button
                        ><button class="score">零分</button>
                      </div>
                    </div>
                    <div>
                      <table style="border-collapse:separate;">
                        <tr>
                          <td><button class="score">1分</button></td>
                          <td><button class="score">3分</button></td>
                          <td><button class="score">5分</button></td>
                          <td><button class="score">退出</button></td>
                        </tr>
                        <div style="height:10px"></div>
                        <tr>
                          <td><button class="score">7分</button></td>
                          <td><button class="score">9分</button></td>
                          <td><button class="score">10分</button></td>
                          <td><button class="score">提交</button></td>
                        </tr>
                      </table>
                    </div>
                  </div>
                </div>
              </div>
            </td>
          </tr>
          <tr>
            <td>
              <div class="card" style="width: 100%; height: 70px">
                <table
                  cellpadding="0"
                  cellspacing="0"
                  style="width: 100%; height: 100%"
                >
                  <tr>
                    <td style="width: 33%; text-align: right">
                      <!-- <table border="1" style="vertical-align:middle"> -->

                      <span
                        class="next"
                        @click="setTitleNumber(currentTitleNumber - 1)"
                        :class="{ unabled: currentTitleNumber - 1 <= 0 }"
                      >
                        <svg-icon className="arrow" iconName="left"></svg-icon>
                        <span style="vertical-align: middle">上一份</span>
                        <!-- </table> -->
                      </span>
                    </td>
                    <td style="text-align: center">标记</td>
                    <td style="width: 33%">
                      <span
                        class="next"
                        @click="setTitleNumber(currentTitleNumber + 1)"
                        :class="{
                          unabled:
                            currentTitleNumber + 1 > titleNumberIndex.length,
                        }"
                      >
                        <span style="vertical-align: middle">下一份</span>
                        <svg-icon className="arrow" iconName="right"></svg-icon>
                      </span>
                    </td>
                  </tr>
                </table>
              </div>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</template>


<script>
import axios from "axios";
import { formatDate } from "@/common.js";
import DialogBox from "./DialogBox.vue";
import Loading from "./Loading.vue";
// import config from "@/config.js";
export default {
  components: {
    DialogBox,
    Loading,
  },
  data() {
    return {
      // examId: 1,
      bankId: 1,
      questions: {
        choice: {},
        multiChoice: {},
        completion: {},
        shortAnswer: {},
      },
      answers: { normal: {} },
      exam: {},
      examPaper: {},
      order: {},
      questionScores: {},
      currentId: 1,
      currentQueType: "choice",
      // config:config
      // user: null,
      titleNumberIndex: [],
      currentTitleNumber: 1,
      // mark:{},

      stopTime: new Date(),
      currentTime: new Date(),
      remainingTime: 0,
      correctTimeDiff: 0,

      showOverDialog: false,
      showJoinDialog: false,

      isLoading: false,
    };
  },
  methods: {
    async getAllExamInfo() {
      this.isLoading = true;
      return axios({
        url: "exam/getAllExamInfo",
        data: {
          examId: this.examId,
          examinee: this.user.id,
        },
      }).then((res) => {
        let data = res.data.data;

        //初始化考试信息
        this.exam = data.exam;
        if (this.exam.type == "fixed")
          this.order = JSON.parse(this.exam.orderJson);
        else if (this.exam.type == "random")
          this.order = JSON.parse(this.examPaper.orderJson);
        this.getTitleNumberIndex();

        //初始化问题信息
        this.questions = {
          choice: {},
          multiChoice: {},
          completion: {},
          shortAnswer: {},
        };
        this.answers = { normal: {} };
        for (let question of data.questions.choice) {
          question.choice = JSON.parse(question.choice);
          // console.log(question.choice);
          this.answers.normal[question.id] = {};
          if (question.type === "choice")
            this.questions.choice[question.id] = question;
          else if (question.type === "multi_choice")
            this.questions.multiChoice[question.id] = question;
        }
        for (let question of data.questions.normal) {
          this.answers.normal[question.id] = {};
          if (question.type === "completion")
            this.questions.completion[question.id] = question;
          else if (question.type === "short_answer")
            this.questions.shortAnswer[question.id] = question;
        }

        //初始化试卷信息
        this.examPaper = data.examPaper;
        if (!this.examPaper) this.showJoinDialog = true;
        else {
          this.stopTime = new Date(
            this.examPaper.startTime + this.exam.duration
          );
        }

        //初始化考生回答信息
        for (let ans of data.answers.normal) {
          this.answers.normal[ans.questionId] = JSON.parse(ans.answer);
        }

        //初始化题目分数信息
        for (let score of data.questionScores) {
          this.questionScores[score.questionId] = score;
        }

        this.setTitleNumber(1);
        this.isLoading = false;
      });
    },
    async getQuestions() {
      return axios({
        url: "question/getAllQuestions",
        data: {
          bankId: this.bankId,
        },
      }).then((res) => {
        this.questions = {
          choice: {},
          multiChoice: {},
          completion: {},
          shortAnswer: {},
        };
        this.answers = { normal: {} };
        for (let question of res.data.data.choice) {
          question.choice = JSON.parse(question.choice);
          // console.log(question.choice);
          this.answers.normal[question.id] = {};
          if (question.type === "choice")
            this.questions.choice[question.id] = question;
          else if (question.type === "multi_choice")
            this.questions.multiChoice[question.id] = question;
        }
        for (let question of res.data.data.normal) {
          this.answers.normal[question.id] = {};
          if (question.type === "completion")
            this.questions.completion[question.id] = question;
          else if (question.type === "short_answer")
            this.questions.shortAnswer[question.id] = question;
        }
      });
    },
    async getExamInfo() {
      return axios({
        url: "/exam/getExams",
        data: {
          id: this.examId,
        },
      }).then((res) => {
        this.exam = res.data.data.data[0];
        // console.log(this.exam);
        this.order = JSON.parse(this.exam.orderJson);
        this.getTitleNumberIndex();
      });
    },
    getExamPaper() {
      axios({
        url: "/exam/getExamPapers",
        data: {
          examinee: 1,
        },
      }).then((res) => {
        this.examPaper = res.data.data.data[0];
        this.stopTime = new Date(this.examPaper.startTime + this.exam.duration);
        // console.log(this.stopTime);
        // console.log(this.exam);
      });
    },
    getAnswers() {
      axios({
        url: "/exam/getAllAnswers",
        data: {
          examinee: 1,
          examId: this.examId,
        },
      }).then((res) => {
        let data = res.data.data;

        for (let ans of data.normal) {
          this.answers.normal[ans.questionId] = JSON.parse(ans.answer);
        }
      });
    },
    getQuestionScores() {
      axios({
        url: "/exam/getQuestionScores",
        data: {
          examId: this.examId,
        },
      }).then((res) => {
        let data = res.data.data;
        for (let score of data) {
          this.questionScores[score.id] = score;
        }
      });
    },
    joinExam() {
      axios({
        url: "/exam/joinExam",
        data: {
          examId: this.examId,
          examinee: this.user.id,
        },
      }).then((res) => {
        this.initExam();
      });
    },
    stopExam() {
      axios({
        url: "/exam/stopExam",
        data: {
          examId: this.examId,
          examinee: this.user.id,
        },
      }).then((res) => {
        this.initExam();
      });
    },
    getScore() {
      axios({
        url: "/exam/correctPaper",
        data: {
          examId: this.examId,
          examinee: this.user.id,
        },
      }).then((res) => {
        this.initExam();
      });
    },
    getQuestionTypeName(type) {
      if (type === "choice") return "单选题";
      else if (type === "multi_choice") return "多选题";
      else if (type === "completion") return "填空题";
      else if (type === "short_answer") return "简答题";
      return "";
    },
    getAnswerType(queType) {
      switch (queType) {
        case "choice":
        case "multi_choice":
        case "completion":
        case "short_answer":
          return "normal";
      }
      return "";
    },
    getTitleNumberIndex() {
      this.titleNumberIndex = [];
      for (let type of this.order.part) {
        for (let queId of this.order[type]) {
          this.titleNumberIndex.push({ id: queId, type: type });
        }
      }
    },
    setTitleNumber(number) {
      if (number <= 0 || number > this.titleNumberIndex.length) return;
      this.currentId = this.titleNumberIndex[number - 1].id;
      this.currentQueType = this.titleNumberIndex[number - 1].type;
      this.currentTitleNumber = number;
    },
    normalQueChange(event, n) {
      let ans =
        this.answers[this.getAnswerType(this.currentQueType)][this.currentId];
      let value = event.target.value;
      if (value == "") {
        if (ans[n]) delete ans[n];
      } else {
        ans[n] = value;
      }
      this.commitAnswer(
        this.currentId,
        this.getAnswerType(this.currentQueType)
      );
    },
    getObjProCount(obj) {
      let count = 0;
      for (let p in obj) {
        if (obj[p]) count++;
      }
      return count;
    },
    commitAnswer(queId, type) {
      if (type == "normal") {
        axios({
          url: "/exam/addNormalAnswer",
          data: {
            questionId: queId,
            examinee: this.user.id,
            examId: this.examId,
            answer: JSON.stringify(this.answers[type][queId]),
          },
          cancelToken: new axios.CancelToken((c) => {
            this.ajaxCancel = c;
          }),
        }).then((res) => {});
      }
    },
    async initExam() {
      // await this.getQuestions();
      // await this.getExamInfo();
      // this.getExamPaper();
      // this.getAnswers();
      // this.getQuestionScores();
      await this.getAllExamInfo();

      if (!this.examPaper) return;
      this.correctTime();

      this.currentTime = new Date() + this.correctTimeDiff;
      let interval = setInterval(() => {
        this.currentTime = new Date(
          new Date().valueOf() + this.correctTimeDiff
        );
        this.remainingTime =
          this.stopTime.valueOf() - this.currentTime.valueOf();
        // console.log(this.currentTime);
        if (this.remainingTime <= 0 || this.isExamOver) {
          this.remainingTime = 0;
          clearInterval(interval);
          this.showOverDialog = true;
        }
      }, 500);
    },
    async correctTime() {
      let before = new Date();
      await axios({
        url: "/exam/getTime",
      }).then((res) => {
        let local = new Date();
        let delay = local - before;
        let correctTime = res.data.data + delay;
        this.correctTimeDiff = correctTime - local;
        // console.log(this.correctTimeDiff);
      });
    },
  },
  computed: {
    user() {
      return this.$store.state.config.user;
    },
    stopTimeString() {
      return formatDate(this.stopTime);
    },
    isExamOver() {
      return this.remainingTime <= 0 || Boolean(this.examPaper.finishTime);
    },
    isExamTimeOut() {
      return this.currentTime > this.exam.latestStartTime + this.exam.duration;
    },
    isExamNotStarted() {
      return this.currentTime < this.exam.earliestStartTime;
    },
  },
  watch: {
    user() {
      if (this.user != null) {
        this.initExam();
      }
    },
  },
  async mounted() {
    // console.log(this.config);
    // console.log(this.questions.choice, this.questions.completion);
    // console.log(this.config);
    if (this.user != null) this.initExam();
    setInterval(() => {
      this.correctTime();
    }, 60000);
  },
  props: {
    examId: {
      type: Number,
      default: 1,
    },
  },
};
</script>

<style scoped>
.exam {
  margin-left: auto;
  margin-right: auto;
}

.card {
  background: #ffffff;
  border-radius: 10px;
  margin: 5px;
  overflow-y: auto;
}

.explain-text {
  font-size: 12px;
  font-family: Microsoft YaHei;
}

.unanswered {
  width: 31px;
  height: 31px;
  border: 1px solid rgba(51, 138, 251, 0.30196078431372547);
  border-radius: 50%;
  display: inline-block;
  margin: 9.5px;
  transition: background 0.3s;
}

.current {
  border-radius: 50%;
  width: 31px;
  height: 31px;
  border: 2px solid #338afb;
  display: inline-block;
  margin: 9.5px;
}

.answered {
  width: 31px;
  height: 31px;
  background: #338afb;
  border: 1px solid rgba(51, 138, 251, 0.30196078431372547);
  border-radius: 50%;
  display: inline-block;
  margin: 9.5px;
}

.current-answered {
  width: 31px;
  height: 31px;
  background: #338afb;
  border: 2px solid #60a2f8;
  border-radius: 50%;
  display: inline-block;
  margin: 9.5px;
}

.marked {
  width: 7px;
  height: 7px;
  background: #338afb;
  border-radius: 50%;
  position: relative;
  left: 21px;
  top: 0px;
  /* margin: 9.5px; */
}

.next {
  cursor: pointer;
  user-select: none;
}

.unabled {
  color: gray;
  cursor: default;
}

.inner {
  width: 100%;
  height: 100%;
}

.inner-number {
  font-size: 14px;
  font-family: Microsoft YaHei;
  color: #000000;
  width: 100%;
  height: 100%;
  text-align: center;
  vertical-align: middle;
  display: table-cell;
}

.answered .inner-number {
  color: white;
}

.arrow {
  width: 30px;
  height: 30px;
  vertical-align: middle;
  /* position: relative;
  top: 5px; */
  /* padding-top: 10px; */
}

.val-text {
  color: #338afb;
}

.description {
  font-weight: bold;
  font-size: 18px;
  display: block;
  margin-bottom: 0px;
}

.choice {
  font-size: 15px;
  color: #aaaaaa;
  cursor: pointer;
}

.completion {
  height: 30px;
  width: 200px;
  border: none;
  border-bottom: 1px solid rgba(153, 153, 153, 0.616);
  outline: none;
}
input[type="radio"] {
  -webkit-appearance: none;
  appearance: none;
  outline: none;
  width: 12px;
  height: 12px;
  border-radius: 50%;
  padding: 2px;
  cursor: pointer;
  vertical-align: center;
  background: #fff;
  border: 1px solid #ccc;
  position: relative;
}

input[type="radio"]:checked {
  background-color: rgb(255, 60, 60);
  border: none;
}

.sandglass {
  color: #338afb;
  width: 20px;
  height: 20px;
  vertical-align: middle;
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
/* Switch开关样式 */
/* 必须是input为 checkbox class 添加 switch 才能实现以下效果 */
input[type="checkbox"].switch {
  outline: none;
  appearance: none;
  -webkit-appearance: none;
  -moz-appearance: none;
  position: relative;
  width: 40px;
  height: 20px;
  background: #ccc;
  border-radius: 10px;
  transition: border-color 0.3s, background-color 0.3s;
}

input[type="checkbox"].switch::after {
  content: "";
  display: inline-block;
  width: 1rem;
  height: 1rem;
  border-radius: 50%;
  background: #fff;
  box-shadow: 0, 0, 2px, #999;
  transition: 0.4s;
  top: 2px;
  position: absolute;
  left: 2px;
}

input[type="checkbox"].switch:checked {
  background: #338afb;
}
/* 当input[type=checkbox]被选中时：伪元素显示下面样式 位置发生变化 */
input[type="checkbox"].switch:checked::after {
  content: "";
  position: absolute;
  left: 55%;
  top: 2px;
}
.score {
  height: 30px;
  width: 60px;
  margin-left: 5px;
  margin-right: 5px;
  border-radius: 5px;
  border: none;
  outline: none;
}
</style>
