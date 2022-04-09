<template>
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
  <!-- 考试结束对话框 -->
  <DialogBox v-model:show="showOverDialog">
    <template v-slot:header>考试已结束</template>
    <template v-if="examPaper && 'grade' in examPaper">
      您的分数：{{ examPaper.grade }}/{{ exam.fullMark }}
      <br/>
      剩余考试次数：{{ exam.repeatTime - examPaper.times }}
      <button
          class="btn"
          @click="
          joinExam();
          showOverDialog = false;
        "
      >
        重新考试
      </button>
    </template>
    <template v-else>
      <button class="btn" @click="getScore()">获取分数</button>
    </template>
    <template v-slot:bottom>
      <button class="btn" @click="showOverDialog = false">确定</button>
    </template>
  </DialogBox>
  <!-- 加入考试对话框 -->
  <DialogBox v-model:show="showJoinDialog">
    <template v-slot:header>加入考试</template>
    <template v-if="isExamNotStarted"> 考试未开始</template>
    <template v-else-if="isExamTimeOut"> 考试已经结束</template>
    <template v-else-if="currentTime >= exam.latestStartTime">
      已错过最晚加入考试的时间
    </template>
    <template v-else>
      考试总分：{{ exam.fullMark }}
      <br/>
      允许重复次数：{{ exam.repeatTime }}
      <br/>
      考试时间：{{ Math.floor(exam.duration / 1000 / 60) }}
      <br/>
      考试类型：{{
        exam.type == "fixed"
            ? "固定"
            : exam.type == "random"
                ? "随机抽题"
                : "未知"
      }}
    </template>
    <template v-slot:bottom>
      <button
          class="btn"
          @click="showJoinDialog = false"
          v-if="
          isExamTimeOut ||
          currentTime >= exam.latestStartTime ||
          isExamNotStarted
        "
      >
        确定
      </button>
      <button
          v-else
          class="btn"
          @click="
          joinExam();
          showJoinDialog = false;
        "
      >
        加入考试
      </button>
    </template>
  </DialogBox>
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
                <table
                    class="explain-text"
                    style="text-align: center; margin: 0 auto; height: 100%"
                >
                  <tr>
                    <td>
                      <div
                          class="current"
                          style="margin: 0 13px 4px 13px"
                      ></div>
                      当前
                    </td>
                    <td>
                      <div
                          class="unanswered"
                          style="margin: 0 13px 4px 13px"
                      ></div>
                      未答
                    </td>
                    <td>
                      <div
                          class="answered"
                          style="margin: 0 13px 4px 13px"
                      ></div>
                      已答
                    </td>
                    <td>
                      <div class="unanswered" style="margin: 0 13px 4px 13px">
                        <div class="marked"></div>
                      </div>
                      标记
                    </td>
                  </tr>
                </table>
              </div>
            </td>
          </tr>
          <tr>
            <td>
              <div class="card" style="width: 262px; height: 506px">
                <table style="width: 100%; height: 100%">
                  <tr>
                    <td style="vertical-align: top; padding-top: 10px">
                      {{ ((serial = 1), (index = {}), undefined) }}
                      <template v-for="queType in order.part" :key="queType">
                        <span
                            style="
                            margin-left: 14px;
                            font-weight: bold;
                            color: black;
                          "
                        >{{ getQuestionTypeName(queType) }}：</span
                        >
                        <br/>
                        <template v-for="queId in order[queType]" :key="queId">
                          {{
                            ((ans = answers[getAnswerType(queType)][queId]),
                                (isAnswered =
                                    (Boolean(ans) || ans == 0) &&
                                    getObjProCount(ans) != 0),
                                (index[queId] = serial),
                                undefined)
                          }}
                          <div
                              class="unanswered"
                              :class="{
                              current: queId == currentId,
                              answered: isAnswered,
                              'current-answered':
                                isAnswered && queId == currentId,
                            }"
                              style="cursor: pointer"
                              @click="
                              currentQueType = queType;
                              currentId = queId;
                              this.currentTitleNumber = index[queId];
                            "
                          >
                            <div v-if="markNumber[serial]" class="marked"></div>
                            <table class="inner">
                              <td class="inner-number">{{ serial++ }}</td>
                            </table>
                          </div>
                        </template>
                        <br/>
                      </template>
                    </td>
                  </tr>
                  <tr>
                    <td style="height: 38px; text-align: center">
                      <button
                          class="btn"
                          style="height: 28px; width: 234px"
                          @click="
                          if (!isExamOver) stopExam();
                          else showOverDialog = true;
                        "
                      >
                        结束考试
                      </button>
                    </td>
                  </tr>
                </table>
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
                            <span style="font-weight: bold">考生：</span
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
                            题量：<span
                              class="val-text"
                              style="margin-right: 28px"
                          >{{ exam.questionsCount }}</span
                          >
                            满分：<span
                              class="val-text"
                              style="margin-right: 28px"
                          >{{ exam.fullMark }}</span
                          >
                            截止时间：<span class="val-text">{{
                              stopTimeString
                            }}</span>
                          </td>
                          <td style="text-align: right">
                            <svg-icon
                                iconName="sandglass"
                                className="sandglass"
                            ></svg-icon>
                            <span style="vertical-align: middle">
                              倒计时：<span class="val-text">{{
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
              <div class="exam-content card" style="width: 100%; height: 426px">
                <template
                    v-if="
                    questions[currentQueType] &&
                    questions[currentQueType][currentId]
                  "
                >
                  <span class="description">
                    ({{
                      questionScores[currentId] &&
                      questionScores[currentId].score
                    }}分)
                    {{
                      currentTitleNumber +
                      "."
                    }}
                    <span v-html="questions[currentQueType][currentId].description"></span>
                  </span>
                  <br/>
                  <template v-if="currentQueType == 'choice'">
                    <template
                        v-for="(choice, index) in questions[currentQueType][
                        currentId
                      ].choice"
                        :key="index"
                    >
                      <el-radio
                          v-model="choiceCheck"
                          :disabled="isExamOver"
                          :label="index"

                      >{{ String.fromCharCode(65 + index) }}.<span v-html="choice"></span></el-radio>
<!--                      <input-->
<!--                          :id="'choice' + index"-->
<!--                          name="choice"-->
<!--                          :value="index"-->
<!--                          type="radio"-->
<!--                          :disabled="isExamOver"-->
<!--                          @click="-->
<!--                          answers[getAnswerType(currentQueType)][currentId] =-->
<!--                            {};-->
<!--                          answers[getAnswerType(currentQueType)][currentId][-->
<!--                            index-->
<!--                          ] = true;-->
<!--                          commitAnswer(-->
<!--                            currentId,-->
<!--                            getAnswerType(currentQueType)-->
<!--                          );-->
<!--                        "-->
<!--                          :checked="-->
<!--                          answers[getAnswerType(currentQueType)][currentId][-->
<!--                            index-->
<!--                          ] == true-->
<!--                        "-->
<!--                      />-->
                      <!--                      <label :for="'choice' + index"-->
                      <!--                      ><span class="choice" v-html="choice"></span></label-->
                      <!--                      >-->
                      <div style="height: 5px"></div>
                    </template>
                  </template>
                  <template v-else-if="currentQueType == 'completion'">
                    <template
                        v-for="n in questions[currentQueType][currentId]
                        .answersCount"
                        :key="currentId + '-' + n"
                    >
                      ({{ n }})
                      <input
                          type="text"
                          class="completion"
                          :disabled="isExamOver"
                          v-model="
                          answers[getAnswerType(currentQueType)][currentId][
                            n - 1
                          ]
                        "
                          @change="normalQueChange($event, n - 1)"
                      />
                    </template>
                  </template>
                  <template v-if="currentQueType == 'multi_choice'">
                    <template
                        v-for="(choice, index) in questions[currentQueType][
                        currentId
                      ].choice"
                        :key="index"
                    >
                      <el-checkbox
                          v-model="answers[getAnswerType(currentQueType)][currentId][index]"
                          :disabled="isExamOver"
                          :label="index"
                          @change="commitAnswer(
                            currentId,
                            getAnswerType(currentQueType)
                          );"
                      >{{ String.fromCharCode(65 + index) }}.<span v-html="choice"></span></el-checkbox>
                      <div style="height: 5px"></div>
                    </template>
                  </template>
                  <template v-else-if="currentQueType == 'short_answer'">
                    <template
                        v-for="n in questions[currentQueType][currentId]
                        .answersCount"
                        :key="currentId + '-' + n"
                    >
                      ({{ n }})
<!--                      <input-->
<!--                          type="text"-->
<!--                          class="completion"-->
<!--                          :disabled="isExamOver"-->
<!--                          v-model="-->
<!--                          answers[getAnswerType(currentQueType)][currentId][-->
<!--                            n - 1-->
<!--                          ]-->
<!--                        "-->
<!--                          @change="normalQueChange($event, n - 1)"-->
<!--                      />-->
                      <el-input
                          v-model="answers[getAnswerType(currentQueType)][currentId][
                            n - 1
                          ]"
                          :rows="5"
                          type="textarea"
                          placeholder="请输入答案"
                          @change="normalQueChange($event, n - 1)"
                          style="margin-top:10px"
                      />
                    </template>
                  </template>
                </template>
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
                        <span style="vertical-align: middle">上一题</span>
                        <!-- </table> -->
                      </span>
                    </td>
                    <td style="text-align: center">
                      <div @click="markNumber[currentTitleNumber]=!markNumber[currentTitleNumber]" class="mark-circle"
                           :class="{'mark-circle-select':markNumber[currentTitleNumber]}">
                        <svg-icon iconName="bookmark"
                                  :className="markNumber[currentTitleNumber]?' mark-icon-select':'mark-icon'"></svg-icon>
                      </div>
                      <br/>
                      <span class="mark-text">标记</span>
                    </td>
                    <td style="width: 33%">
                      <span
                          class="next"
                          @click="setTitleNumber(currentTitleNumber + 1)"
                          :class="{
                          unabled:
                            currentTitleNumber + 1 > titleNumberIndex.length,
                        }"
                      >
                        <span style="vertical-align: middle">下一题</span>
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
import {formatDate} from "@/common.ts";
import DialogBox from "./DialogBox.vue";
import Loading from "./Loading.vue";
import SvgIcon from "@/components/Svgicon";
// import config from "@/config.ts";
export default {
  components: {
    SvgIcon,
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
      answers: {normal: {}},
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

      markNumber: {},
      choiceCheck: -1,
      // multiChoiceCheck:[]
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
          "multi_choice": {},
          completion: {},
          "short_answer": {},
        };
        this.answers = {normal: {}};
        for (let question of data.questions.choice) {
          question.choice = JSON.parse(question.choice);
          // console.log(question.choice);
          this.answers.normal[question.id] = {};
          if (question.type === "choice")
            this.questions.choice[question.id] = question;
          else if (question.type === "multi_choice")
            this.questions["multi_choice"][question.id] = question;
        }
        for (let question of data.questions.normal) {
          this.answers.normal[question.id] = {};
          if (question.type === "completion")
            this.questions.completion[question.id] = question;
          else if (question.type === "short_answer")
            this.questions["short_answer"][question.id] = question;
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
        this.updateChoice();
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
        this.answers = {normal: {}};
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
          this.titleNumberIndex.push({id: queId, type: type});
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
      // let value = event.target.value;
      // if (value == "") {
      //   if (ans[n]) delete ans[n];
      // } else {
      //   ans[n] = value;
      // }
      if (!ans[n]) delete ans[n];
      this.commitAnswer(
          this.currentId,
          this.getAnswerType(this.currentQueType)
      );
    },
    // shortAnswerQueChange(event, n) {
    //   let ans =
    //       this.answers[this.getAnswerType(this.currentQueType)][this.currentId];
    //   let value = event.target.value;
    //   if (value == "") {
    //     if (ans[n]) delete ans[n];
    //   } else {
    //     ans[n] = value;
    //   }
    //   this.commitAnswer(
    //       this.currentId,
    //       this.getAnswerType(this.currentQueType)
    //   );
    // },
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
        }).then((res) => {
        });
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
    updateChoice() {
      const question = this.questions[this.currentQueType][this.currentId];
      // console.log(question);
      if (question.type === "choice") {
        this.choiceCheck = -1;
        for (let key in this.answers["normal"][question.id]) {
          if (this.answers["normal"][question.id][key]) {
            this.choiceCheck = Number.parseInt(key);
            break;
          }
        }
      }
    }
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
    currentTitleNumber(val) {
      this.updateChoice();
    },
    choiceCheck(val) {
      if (val === -1)
        return;
      // console.log(val);
      const question = this.questions[this.currentQueType][this.currentId];
      this.answers["normal"][question.id] = {};
      this.answers["normal"][question.id][val] = true;
      this.commitAnswer(this.currentId, this.getAnswerType(this.currentQueType));
    }
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

<style lang="scss" scoped>
@import "src/styles/variables";

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

.marked {
  width: 8px;
  height: 8px;
  background: $primary-color;
  border-radius: 50%;
  position: absolute;
  left: 21px;
  top: 0px;
  /* margin: 9.5px; */
  border-style: solid;
  border-color: white;
  border-width: 1px;
}

.unanswered {
  width: 31px;
  height: 31px;
  border: 1px solid rgba(51, 138, 251, 0.30196078431372547);
  border-radius: 50%;
  display: inline-block;
  margin: 8px;
  transition: background 0.3s;
  position: relative;
}

.current {
  border-radius: 50%;
  width: 31px;
  height: 31px;
  border: 2px solid $primary-color;
  display: inline-block;
  margin: 8px;
  position: relative;

  .marked {
    left: 20px;
    top: -1px;
  }
}

.answered {
  width: 31px;
  height: 31px;
  background: $primary-color;
  border: 1px solid rgba(51, 138, 251, 0.30196078431372547);
  border-radius: 50%;
  display: inline-block;
  margin: 8px;
  position: relative;
}

.current-answered {
  width: 31px;
  height: 31px;
  background: $primary-color;
  border: 2px solid #60a2f8;
  border-radius: 50%;
  display: inline-block;
  margin: 8px;
  position: relative;

  .marked {
    left: 20px;
    top: -1px;
  }
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
  color: $primary-color;
}

.exam-content {
  padding: 20px;
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
  color: $primary-color;
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
  z-index: 30;
}

.mark-icon {
  width: 24px;
  height: 24px;
  color: $primary-color;
}

.mark-icon-select {
  width: 24px;
  height: 24px;
  color: white;
}

.mark-circle {
  width: 35px;
  height: 35px;
  //background: $primary-color;
  border-style: solid;
  border-width: 2px;
  border-color: $primary-color;
  border-radius: 50%;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}

.mark-circle-select {
  background: $primary-color;
}

.mark-text {
  font-size: 14px;
  color: $primary-color;
}
</style>
