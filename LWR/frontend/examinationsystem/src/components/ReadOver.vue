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
                <table style="margin: 10px auto auto">
                  <tr>
                    <td class="info1">本题满分:</td>
                    <td><span
                        class="info1 blue">{{
                        questions[currentQuestionNumber] && questionScores[questions[currentQuestionNumber].id].score
                      }}</span>
                    </td>
                    <td class="info1">分</td>
                    <td style="padding-left: 17px" class="info1">自动阅卷</td>
                  </tr>
                  <tr>
                    <td class="info1">给分间隔:</td>
                    <td>
                      <span class="info1 blue">0.5</span>
                    </td>
                    <td class="info1">分</td>
                    <td style="padding-left: 28px">
                      <!--                      <input type="checkbox" class="switch"/>-->
                      <el-switch v-model="isAutoCorrect"/>
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
                  style="width: 262px; height: 506px; padding:20px 0 20px 20px"
              >
                <el-scrollbar>
                  <div class="info1">题目列表:</div>
                  <br/>
                  <div class="question" :class="{'question-selected':index===currentQuestionNumber}"
                       v-for="(question,index) in questions" :key="question.id"
                       @click="currentQuestionNumber=index">
                    <div
                        style="display: flex;font-size: 14px;width: 210px">
                      <span :class="{blue:index===currentQuestionNumber}"
                            style="text-overflow:ellipsis;white-space: nowrap;overflow-x: hidden;width: 180px">
                        {{ index + 1 }}.{{ question.description }}</span>
                      <span
                          v-if="answers[questions[index].id]&&answers[questions[index].id].score!=null"
                          style="color:#67C23A;margin-left: auto;font-size: 12px">
                          {{ answers[questions[index].id].score }}分
                        </span>
                      <span v-else style="color: #F56C6C;margin-left: auto;font-size: 12px">未批</span>
                    </div>
                    <el-progress style="width: 235px"
                                 :percentage="Math.ceil(correctedNum[questions[index].id]/correctInfo.data.length*100)"/>
                  </div>
                </el-scrollbar>
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
                          >{{ correctInfo.corrected.size }}</span>
                            <span style="color:black;margin-right: 28px">/{{ correctInfo.count }}</span>
                            当前：<span class="val-text">{{ currentTitleNumber + 1 }}</span><span
                              style="color:black;margin-right: 28px">/{{ correctInfo.count }}</span>
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
                  <div style="height: 50%;width: 100%">
                    <div class="info1">
                      当前题目:
                    </div>
                    <div>
                      {{ questions[currentQuestionNumber] && questions[currentQuestionNumber].description }}
                    </div>
                  </div>
                  <div style="height: 50%; width: 100%">
                    <div class="info1">
                      参考答案:
                    </div>
                    <div>
                      <template v-if="questions[currentQuestionNumber]">
                        <template v-if="getQuestionType(questions[currentQuestionNumber].type)==='choice'">
                          {{ getChoiceCorrectAnswer(questions[currentQuestionNumber].answer) }}
                        </template>
                        <div v-else v-html="getNormalCorrectAnswer(questions[currentQuestionNumber].answer)">

                        </div>
                      </template>
                    </div>
                  </div>
                </div>
                <div
                    class="line"
                    style="
                    height: 100%;
                    border: 1px solid #707070;
                    width: 0;
                    border-left-style: none;
                  "
                ></div>
                <div style="height: 100%; width: 55%">
                  <div style="width: 100%; height: 70%; padding: 20px">
                    <div
                        class="info1"
                    >
                      考生答案:
                    </div>
                    <div>
                      <template v-if="questions[currentQuestionNumber]">
                        <template v-if="getQuestionType(questions[currentQuestionNumber].type)==='choice'">
                          {{
                            getChoiceCorrectAnswer(answers[questions[currentQuestionNumber].id] ? answers[questions[currentQuestionNumber].id].answer : undefined)
                          }}
                        </template>
                        <div v-else
                             v-html="getNormalCorrectAnswer(answers[questions[currentQuestionNumber].id] ? answers[questions[currentQuestionNumber].id].answer : undefined)">
                        </div>
                      </template>
                    </div>
                  </div>
                  <div
                      class="line"
                      style="
                      width: 100%;
                      border: 1px solid #707070;
                      height: 0;
                      border-bottom-style: none;
                    "
                  ></div>
                  <div
                      style="
                      width: 100%;
                      height: 29.8%;
                      padding: 20px;
                      display: flex;
                    "
                  >
                    <div style="width: 100%">
                      <el-row>
                        <el-col :span="8">
                          <span class="info1"> 分值: </span>
                          <!--                          <el-input-->
                          <!--                              clearable-->
                          <!--                              v-model.number="score"-->
                          <!--                              size="default"-->
                          <!--                              placeholder="请输入分值"-->
                          <!--                              input-style="width:110px"-->
                          <!--                          />-->
                          <el-input-number
                              v-model="score"
                              size="default"
                              :min="0"
                              :max="questions[currentQuestionNumber]?questionScores[questions[currentQuestionNumber].id].score:10"
                              controls-position="right"
                              style="width:110px"
                              :step="0.5"
                          />
                        </el-col>
                        <el-col v-for="n in 3" :span="4" :key="n">
                          <el-button
                              type="info"
                              plain
                              style="height: 30px; width: 67px"
                              size="small"
                              @click="score=2*n-1"
                              :disabled="questions[currentQuestionNumber]&&2*n-1>questionScores[questions[currentQuestionNumber].id].score"
                          >{{ 2 * n - 1 }}分
                          </el-button
                          >
                        </el-col>
                        <el-col :span="4">
                          <el-button
                              style="
                              height: 30px;
                              width: 80px;
                              display: block;
                              margin: auto;
                            "
                              size="small"
                              type="danger"
                          >退出
                          </el-button
                          >
                        </el-col>
                      </el-row>
                      <el-row style="height: 5px"/>
                      <el-row>
                        <el-col :span="4">
                          <el-button
                              style="height: 30px; width: 67px"
                              size="small"
                              plain
                              type="success"
                              @click="score=questionScores[questions[currentQuestionNumber].id].score"
                          >满分
                          </el-button
                          >
                        </el-col>
                        <el-col :span="4">
                          <el-button
                              style="height: 30px; width: 67px"
                              size="small"
                              plain
                              type="danger"
                              @click="score=0"
                          >零分
                          </el-button
                          >
                        </el-col>
                        <el-col v-for="n in 3" :span="4" :key="n">
                          <el-button
                              type="info"
                              plain
                              style="height: 30px; width: 67px"
                              size="small"
                              @click="score=2*n+5"
                              :disabled="questions[currentQuestionNumber]&&2*n+5>questionScores[questions[currentQuestionNumber].id].score"
                          >{{ 2 * n + 5 }}分
                          </el-button
                          >
                        </el-col>
                        <el-col :span="4">
                          <el-button
                              style="
                              height: 30px;
                              width: 80px;
                              display: block;
                              margin: auto;
                            "
                              size="small"
                              type="primary"
                              @click="commitScore"
                          >提交
                          </el-button
                          >
                        </el-col>
                      </el-row>
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
                          :class="{ unabled: currentTitleNumber - 1 < 0 }"
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
                            currentTitleNumber+1 >= correctInfo.data.length,
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


<script lang="js">
import axios from "axios";
import {formatDate, getQuestionGeneralType} from "@/common.ts";
import DialogBox from "./DialogBox.vue";
import Loading from "./Loading.vue";
import {ElMessage} from "element-plus";
// import config from "@/config.js";
export default {
  components: {
    DialogBox,
    Loading,
  },
  data() {
    return {
      examId: 1,
      bankId: 1,
      questions: [],
      answers: {},
      currentAnswer: {},
      exam: {},
      correctInfo: {data: [], count: 0, corrected: new Set()},
      examPaper: {},
      order: {},
      questionScores: {},
      currentId: 1,
      currentQueType: "choice",
      // config:config
      // user: null,
      titleNumberIndex: [],
      currentTitleNumber: 0,
      currentQuestionNumber: 0,
      remainingNum: 0,
      // mark:{},

      stopTime: new Date(),
      currentTime: new Date(),
      remainingTime: 0,
      correctTimeDiff: 0,
      correctedNum: {},

      score: 0,
      isAutoCorrect: false,
      loadingNum: 0
    };
  },
  methods: {
    async getAllInfo() {
      // this.$store.state.config.showLoading = true;
      this.loadingNum++;
      this.examId = this.$route.params['examId'];
      return axios({
        url: "examCorrect/getAllCorrectInfo",
        data: {
          examId: this.examId,
          correctorId: this.user.id,
        },
      }).then((res) => {
        let data = res.data.data;

        //初始化考试信息
        this.exam = data.exam;

        //初始化考试批阅信息
        this.correctInfo = data.correctInfo;
        this.correctInfo.corrected = new Set();
        for (let info of this.correctInfo.data) {
          if (info['grade']) {
            this.correctInfo.corrected.add(info.id);
          }
        }

        //初始化问题信息
        this.questions = [];
        // this.answers = {normal: {}};
        const regex = /<\/?.+?\/?>/gm;
        for (let question of data.questions.choice) {
          question.choice = JSON.parse(question.choice);
          question.answer = JSON.parse(question.answer);
          question.description = question.description.replace(regex, "").replace(/&nbsp;/gi, "");
          // console.log(question.choice);
          // this.answers.normal[question.id] = {};
          // if (question.type === "choice")
          //   this.questions.choice[question.id] = question;
          // else if (question.type === "multi_choice")
          //   this.questions.multiChoice[question.id] = question;
          this.questions.push(question);
        }
        for (let question of data.questions.normal) {
          question.description = question.description.replace(regex, "").replace(/&nbsp;/gi, "");
          question.answer = JSON.parse(question.answer);
          // this.answers.normal[question.id] = {};
          // if (question.type === "completion")
          //   this.questions.completion[question.id] = question;
          // else if (question.type === "short_answer")
          //   this.questions.shortAnswer[question.id] = question;
          this.questions.push(question);
        }
        // console.log(data.questions.correctedNum)
        this.correctedNum = data.questions.correctedNum;

        // console.log(this.questions);

        //初始化题目分数信息
        for (let score of data.questionScores) {
          this.questionScores[score.questionId] = score;
        }

        this.getAnswers();

      }).finally(() => {
        // this.$store.state.config.showLoading = false;
        this.loadingNum--;
      });
    },
    getAnswers() {
      // this.$store.state.config.showLoading = true;
      this.loadingNum++;
      return axios({
        url: "/exam/getAllAnswers",
        data: {
          examinee: this.correctInfo.data[this.currentTitleNumber].examinee,
          examId: this.examId,
        },
      }).then((res) => {
        let data = res.data.data;

        this.remainingNum = this.questions.length;
        this.answers = {};
        for (let ans of data.normal) {
          this.answers[ans.questionId] = ans;
          this.answers[ans.questionId].answer = JSON.parse(ans.answer);
          if (ans.score !== undefined)
            this.remainingNum--;
        }
        for (let ans of data.answers) {
          if (!this.answers[ans.questionId]) {
            this.answers[ans.questionId] = ans;
            if (ans.score !== undefined)
              this.remainingNum--;
          }
        }
        this.autoCorrectObjective();
        this.updateCurrentAnswer(this.currentQuestionNumber);
      }).finally(() => {
        // this.$store.state.config.showLoading = false;
        this.loadingNum--;
      });
    },
    commitScore() {
      // this.$store.state.config.showLoading = true;
      this.loadingNum++;
      const score = this.score;
      axios({
        url: "examCorrect/setScore",
        data: {
          questionId: this.questions[this.currentQuestionNumber].id,
          examId: this.examId,
          examinee: this.correctInfo.data[this.currentTitleNumber].examinee,
          score: score,
          corrector: this.user.id
        }
      }).then((res) => {
        if (res.data.errCode !== 0) {
          throw new Error();
        } else {
          this.answers[this.questions[this.currentQuestionNumber].id].score = score;
          this.remainingNum--;
          if (this.remainingNum <= 0) {
            this.remainingNum = 0;
            this.calculateGrade();
          }
          this.correctedNum[this.questions[this.currentQuestionNumber].id]++;
        }
      }).catch(() => {
        ElMessage({message: '提交失败，请重试', type: 'error'});
      }).finally(() => {
        // this.$store.state.config.showLoading = false;
        this.loadingNum--;
      });
    },
    calculateGrade() {
      // this.$store.state.config.showLoading = true;
      this.loadingNum++;
      axios({
        url: "examCorrect/calculateScore",
        data: {
          examId: this.examId,
          examinee: this.correctInfo.data[this.currentTitleNumber].examinee,
        }
      }).then((res) => {
        if (res.data.errCode !== 0) {
          throw new Error();
        } else {
          ElMessage({message: '试卷批改完成', type: 'success'});
          this.correctInfo.data[this.currentTitleNumber].grade = res.data.data;
          this.correctInfo.corrected.add(this.correctInfo.data[this.currentTitleNumber].id);
        }
      }).catch(() => {
        ElMessage({message: '计算分数失败', type: 'error'});
      }).finally(() => {
        // this.$store.state.config.showLoading = false;
        this.loadingNum--;
      });
    },
    //自动批阅客观题
    autoCorrectObjective() {
      if (!this.isAutoCorrect)
        return;
      if (!this.correctInfo.data[this.currentTitleNumber].objectiveAutoCorrected) {
        // this.$store.state.config.showLoading = true;
        this.loadingNum++;
        axios({
          url: "examCorrect/autoCorrectObjective",
          data: {
            examId: this.examId,
            examinee: this.correctInfo.data[this.currentTitleNumber].examinee,
          }
        }).then((res) => {
          if (res.data.errCode !== 0) {
            throw new Error();
          } else {
            ElMessage({message: '客观题自动批改完成', type: 'success'});
            this.correctInfo.data[this.currentTitleNumber].objectiveAutoCorrected = true;
            this.getAnswers();
            for (const id of res.data.data) {
              this.correctedNum[id]++;
            }
            this.remainingNum -= res.data.data.length;
            if (this.remainingNum <= 0) {
              this.remainingNum = 0;
              this.calculateGrade();
            }
          }
        }).catch(() => {
          ElMessage({message: '客观题自动批改失败', type: 'error'});
        }).finally(() => {
          // this.$store.state.config.showLoading = false;
          this.loadingNum--;
        });
      }
    },
    updateCurrentAnswer(val) {
      if (this.answers[this.questions[val].id]) {
        this.currentAnswer = this.answers[this.questions[val].id];
      } else {
        this.currentAnswer = {
          examId: this.examId,
          examinee: this.correctInfo.data[this.currentTitleNumber].examinee,
          questionId: this.questions[val].id,
        };
        this.answers[this.questions[val].id] = this.currentAnswer;
      }
      if (this.currentAnswer.score)
        this.score = this.currentAnswer.score;
      else
        this.score = 0;
    },
    async getQuestions() {
      return axios({
        url: "question/getAllQuestions",
        data: {
          bankId: this.bankId,
          examId: this.examId
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
      if (number < 0 || number >= this.correctInfo.data.length) return;
      this.currentTitleNumber = number;
      this.getAnswers();
      // this.currentId = this.titleNumberIndex[number - 1].id;
      // this.currentQueType = this.titleNumberIndex[number - 1].type;
      // this.currentTitleNumber = number;
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
    async initExam() {
      // await this.getQuestions();
      // await this.getExamInfo();
      // this.getExamPaper();
      // this.getAnswers();
      // this.getQuestionScores();
      await this.getAllInfo();


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
    getQuestionType(name) {
      return getQuestionGeneralType(name);
    },
    getChoiceCorrectAnswer(answer) {
      let result = "";
      let keys = [];
      for (const key in answer) {
        const index = Number.parseInt(key);
        if (!isNaN(index))
          keys.push(index);
      }
      keys.sort();
      for (const index of keys) {
        if (answer[index]) {
          if (result !== "")
            result += ",";
          result += String.fromCharCode(65 + index);
        }
      }
      return result;
    },
    getNormalCorrectAnswer(answer) {
      let result = "";
      let keys = [];
      for (const key in answer) {
        const index = Number.parseInt(key);
        if (!isNaN(index))
          keys.push(index);
      }
      keys.sort();
      for (const index of keys) {
        if (result !== "")
          result += "<br/>";
        result += `(${index + 1})`;
        if (answer[index]) {
          result += answer[index];
        }
      }
      return result;
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
    }
  },
  watch: {
    user() {
      if (this.user != null) {
        this.initExam();
      }
    },
    currentQuestionNumber(val) {
      // let answer;
      this.updateCurrentAnswer(val);
      this.autoCorrectObjective();
    },
    isAutoCorrect() {
      this.autoCorrectObjective();
    },
    loadingNum(val) {
      this.$store.state.config.showLoading = val > 0;
    }
    // score(val) {
    //   if (val > this.answers[this.questions[index].id].score) {
    //     this.score = this.answers[this.questions[index].id].score;
    //   }
    // }
  },
  async mounted() {
    if (this.user != null) this.initExam();
  },
  // props: {
  //   examId: {
  //     type: Number,
  //     default: 1,
  //   },
  // },
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
  box-shadow: 0 0 2px #999;
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

.info1 {
  font-size: 17px;
  font-weight: bold;
  color: #000000;
  font-family: Microsoft YaHei, serif;
}

.blue {
  color: $primary-color;
}

.question {
  margin-bottom: 9px;
  width: 225px;
  cursor: pointer;
  transition: background-color 0.5s;
  padding: 5px;
  border-radius: 5px;

  &:hover {
    background-color: #d0e4fc;
  }
}

.question-selected {
  background-color: #d0e4fc;
}
</style>
