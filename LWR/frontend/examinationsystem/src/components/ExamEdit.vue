<template>
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
  <!-- <DialogBox v-model:show="showOverDialog">
    <template v-slot:header>考试已结束</template>
    <template v-if="examPaper && 'grade' in examPaper">
      您的分数：{{ examPaper.grade }}/{{ exam.fullMark }}
      <br />
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
  <DialogBox v-model:show="showJoinDialog">
    <template v-slot:header>加入考试</template>
    <template v-if="isExamNotStarted"> 考试未开始 </template>
    <template v-else-if="isExamTimeOut"> 考试已经结束 </template>
    <template v-else-if="currentTime >= exam.latestStartTime">
      已错过最晚加入考试的时间
    </template>
    <template v-else>
      考试总分：{{ exam.fullMark }}
      <br />
      允许重复次数：{{ exam.repeatTime }}
      <br />
      考试时间：{{ Math.floor(exam.duration / 1000 / 60) }}
      <br />
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
  </DialogBox> -->
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
              <div
                  class="card"
                  style="
                  height: 84px;
                  margin-top: 0;
                  display: flex;
                  flex-wrap: wrap;
                  align-items: center;
                  padding: 10px;
                  font-size: 12px;
                  justify-content: space-between;
                "
              >
                <div>
                  类型：
                  <DropDown
                      :width="93"
                      :height="20"
                      :values="[
                      { value: 'fixed', text: '固定题' },
                      { value: 'random', text: '随机抽题' },
                    ]"
                      @change="onTypeChange"
                  ></DropDown>
                </div>
                <div>
                  考试结束后计算分数：
                  <DropDown
                      :width="60"
                      :height="20"
                      :values="[
                      { value: 'true', text: '是' },
                      { value: 'false', text: '否' },
                    ]"
                      @change="onCalAtOnceChange"
                  ></DropDown>
                </div>
                <div>
                  允许重复次数：<input
                    type="text"
                    class="inputBox"
                    style="width: 50px; height: 20px; text-align: center"
                    v-model="exam.repeatTime"
                />
                </div>
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
                        <span
                            class="order"
                            style="margin-left: 20px"
                            @click="subPartOrder(queType)"
                        >上移</span
                        >
                        <span class="order" @click="addPartOrder(queType)"
                        >下移</span
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
                          style="height: 28px; width: 234px; margin-bottom: 10px"
                          @click="addQuestion()"
                      >
                        添加题目
                      </button>
                      <button
                          class="btn"
                          style="height: 28px; width: 234px"
                          @click="
                          commitExam();
                          $router.replace('/teacher/examManage');
                        "
                      >
                        结束编辑
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
                                  <!-- {{ exam.title }} -->
                                  <input
                                      placeholder="标题"
                                      style="font-size: 21px; font-weight: bold"
                                      class="input"
                                      v-model="exam.title"
                                  />
                                </td>
                                <td
                                    style="
                                    font-size: 14px;
                                    opacity: 0.7;
                                    vertical-align: bottom;
                                  "
                                >
                                  <!-- {{ exam.subtitle }} -->
                                  <input
                                      placeholder="全名"
                                      style="font-size: 14px; font-weight: bold"
                                      class="input"
                                      v-model="exam.subtitle"
                                  />
                                </td>
                              </tr>
                            </table>
                          </td>
                          <td style="text-align: right; font-size: 21px">
                            <button
                                class="btn"
                                style="width: 44px; height: 21px; font-size: 11px"
                            >
                              预览
                            </button>
                            <!-- <span style="font-weight: bold">考生：</span> -->
                            <!-- {{ user && user.nickname }} -->
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
                          >{{ titleNumberIndex.length }}</span
                          >
                            满分：<span
                              class="val-text"
                              style="margin-right: 28px"
                          >{{ fullMark }}</span
                          >
                            最早开始时间：
                            <input
                                style="height: 18px; width: 126px"
                                placeholder="xxxx-xx-xx xx:xx:xx"
                                class="input"
                                v-model="exam.earliestStartTime"
                            />
                            <!-- <span class="val-text">{{
                              stopTimeString
                            }}</span> -->
                            最晚开始时间：
                            <input
                                style="height: 18px; width: 126px"
                                placeholder="xxxx-xx-xx xx:xx:xx"
                                class="input"
                                v-model="exam.latestStartTime"
                            />
                            <!-- <span class="val-text">{{
                              stopTimeString
                            }}</span> -->
                          </td>
                          <td style="text-align: right">
                            <svg-icon
                                iconName="sandglass"
                                className="sandglass"
                            ></svg-icon>
                            <span style="vertical-align: middle">
                              考试时间：
                              <!-- <span class="val-text">{{
                                Math.floor(remainingTime / 1000 / 60)
                              }}</span> -->
                              <input
                                  id="minute"
                                  style="
                                  height: 18px;
                                  width: 35px;
                                  text-align: center;
                                "
                                  class="input"
                                  @change="calDuration()"
                              />
                              分钟
                              <!-- <span class="val-text">{{
                                Math.floor((remainingTime / 1000) % 60)
                              }}</span> -->
                              <input
                                  id="second"
                                  style="
                                  height: 18px;
                                  width: 35px;
                                  text-align: center;
                                "
                                  class="input"
                                  @change="calDuration()"
                              />
                              秒
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
                <div v-if="this.order.part && this.order.part.length > 0">
                  <div style="width: 100%; height: 40px">
                    第{{ currentTitleNumber }}题 分数：<input
                      style="height: 25px; width: 40px; text-align: center"
                      class="input"
                      v-model.number="score"
                  />
                    <span
                        class="order"
                        style="margin-left: 20px"
                        @click="subCurrentOrder()"
                    >上移</span
                    >
                    <span class="order" @click="addCurrentOrder()">下移</span>
                    <span
                        class="order"
                        style="margin-left: 10px"
                        @click="deleteQuestion()"
                    >删除题目</span
                    >
                  </div>
                  <div style="width: 100%">
                    <QuestionEdit
                        :questionBefore="questions[currentQueType][currentId]"
                        @save="saveQuestionScore"
                    ></QuestionEdit>
                  </div>
                </div>
                <div v-else style="width:100%;height:100%">
                  请在左侧创建题目
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
                        <span style="vertical-align: middle">上一题</span>
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
import DropDown from "./DropDown.vue";
import QuestionEdit from "./QuestionEdit";
// import config from "@/config.ts";
export default {
  components: {
    DialogBox,
    Loading,
    DropDown,
    QuestionEdit,
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
      score: 0,
      questionScores: {},
      currentId: 1,
      currentQueType: "choice",
      // config:config
      // user: null,
      titleNumberIndex: [],
      currentTitleNumber: 0,
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
        //转换日期格式
        this.exam.earliestStartTime = formatDate(
            new Date(this.exam.earliestStartTime)
        );
        this.exam.latestStartTime = formatDate(
            new Date(this.exam.latestStartTime)
        );
        //转换duration
        let minute = document.getElementById("minute");
        let second = document.getElementById("second");
        minute.value = Math.floor(this.exam.duration / 1000 / 60);
        second.value = Math.floor((this.exam.duration / 1000) % 60);
        //初始化题目顺序
        // if (this.exam.type == "fixed")
        this.order = JSON.parse(this.exam.orderJson);
        // else if (this.exam.type == "random")
        //   this.order = JSON.parse(this.examPaper.orderJson);
        this.getTitleNumberIndex();
        if (this.titleNumberIndex[0]) {
          this.currentTitleNumber = 1;
          this.currentQueTyp = this.titleNumberIndex[0].type;
          this.currentId = this.titleNumberIndex[0].id;
        }

        //初始化问题信息
        this.questions = {
          choice: {},
          multiChoice: {},
          completion: {},
          shortAnswer: {},
        };
        this.answers = {normal: {}};
        for (let question of data.questions.choice) {
          question.choice = JSON.parse(question.choice);
          question.answer = JSON.parse(question.answer);
          // console.log(question.choice);
          this.answers.normal[question.id] = {};
          if (question.type == "choice")
            this.questions.choice[question.id] = question;
          else if (question.type == "multi_choice")
            this.questions.multiChoice[question.id] = question;
        }
        for (let question of data.questions.normal) {
          question.answer = JSON.parse(question.answer);
          this.answers.normal[question.id] = {};
          if (question.type == "completion")
            this.questions.completion[question.id] = question;
          else if (question.type == "short_answer")
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
        // for (let ans of data.answers.normal) {
        //   this.answers.normal[ans.questionId] = JSON.parse(ans.answer);
        // }

        //初始化题目分数信息
        for (let score of data.questionScores) {
          this.questionScores[score.questionId] = score;
        }

        this.isLoading = false;
      });
    },
    calDuration() {
      let minute = Number(document.getElementById("minute").value);
      let second = Number(document.getElementById("second").value);
      this.exam.duration = (second + minute * 60) * 1000;
    },
    saveQuestionScore(question) {
      let score = this.score;

      let before = this.questions[this.currentQueType][this.currentId];

      if (before.type !== question.type) {
        // console.log("dfd")
        let index = this.order[this.currentQueType].indexOf(before.id);
        this.order[this.currentQueType].splice(index, 1);
        if (this.order[this.currentQueType].length === 0) {
          this.order.part.splice(this.order.part.indexOf(this.currentQueType));
        }

        if (!this.order[question.type]) this.order[question.type] = [];
        this.order[question.type].push(before.id);
        if (this.order[question.type].length === 1) {
          if (!this.order.part) this.order.part = [];
          this.order.part.push(question.type);
        }
        this.currentQueType = question.type;
        this.getTitleNumberIndex();
      }

      this.questions[this.currentQueType][this.currentId] = question;

      axios({
        url: "exam/addQuestionScore",
        data: {
          questionId: question.id,
          examId: this.examId,
          score: this.score,
        },
      })
          .then((res) => {
            if (res.data.errCode != 0) {
              alert("修改失败");
            } else {
              this.questionScores[question.id].score = score;
              if (before.type != question.type) this.commitExam();
            }
          })
          .catch(() => {
            alert("修改失败");
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
      if (type == "choice") return "单选题";
      else if (type == "multi_choice") return "多选题";
      else if (type == "completion") return "填空题";
      else if (type == "short_answer") return "简答题";
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
    addCurrentOrder() {
      let titleNumber = this.currentTitleNumber - 1;
      if (
          titleNumber >= this.titleNumberIndex.length - 1 ||
          this.titleNumberIndex[titleNumber].type !=
          this.titleNumberIndex[titleNumber + 1].type
      )
        return;
      let temp = this.titleNumberIndex[titleNumber + 1];
      this.titleNumberIndex[titleNumber + 1] =
          this.titleNumberIndex[titleNumber];
      this.titleNumberIndex[titleNumber] = temp;

      let index = this.order[this.currentQueType].indexOf(this.currentId);
      temp = this.order[this.currentQueType][index + 1];
      this.order[this.currentQueType][index + 1] =
          this.order[this.currentQueType][index];
      this.order[this.currentQueType][index] = temp;

      this.currentTitleNumber++;
    },
    subCurrentOrder() {
      let titleNumber = this.currentTitleNumber - 1;
      if (
          titleNumber <= 0 ||
          this.titleNumberIndex[titleNumber].type !=
          this.titleNumberIndex[titleNumber - 1].type
      )
        return;
      let temp = this.titleNumberIndex[titleNumber - 1];
      this.titleNumberIndex[titleNumber - 1] =
          this.titleNumberIndex[titleNumber];
      this.titleNumberIndex[titleNumber] = temp;

      let index = this.order[this.currentQueType].indexOf(this.currentId);
      temp = this.order[this.currentQueType][index - 1];
      this.order[this.currentQueType][index - 1] =
          this.order[this.currentQueType][index];
      this.order[this.currentQueType][index] = temp;

      this.currentTitleNumber--;
    },
    addPartOrder(part) {
      let index = this.order.part.indexOf(part);
      if (index >= this.order.part.length - 1) return;
      let temp = this.order.part[index + 1];
      this.order.part[index + 1] = this.order.part[index];
      this.order.part[index] = temp;
      this.getTitleNumberIndex();
      this.currentTitleNumber =
          this.titleNumberIndex.findIndex((value) => {
            return value.id == this.currentId;
          }) + 1;
    },
    subPartOrder(part) {
      let index = this.order.part.indexOf(part);
      if (index <= 0) return;
      let temp = this.order.part[index - 1];
      this.order.part[index - 1] = this.order.part[index];
      this.order.part[index] = temp;
      this.getTitleNumberIndex();
      this.currentTitleNumber =
          this.titleNumberIndex.findIndex((value) => {
            return value.id == this.currentId;
          }) + 1;
    },
    commitExam() {
      let data = Object.assign({}, this.exam);
      data.earliestStartTime = new Date(data.earliestStartTime);
      data.latestStartTime = new Date(data.latestStartTime);
      data.orderJson = JSON.stringify(this.order);
      data.fullMark = this.fullMark;

      // console.log(data);

      axios({
        url: "exam/addExam",
        data: data,
      })
          .then((res) => {
            if (res.data.errCode != 0) alert("保存失败");
          })
          .catch(() => {
            alert("保存失败");
          });
    },
    onTypeChange(value) {
      this.exam.type = value;
    },
    onCalAtOnceChange(value) {
      this.calGradeAtOnce = value == "true";
    },
    addQuestion() {
      let question = {
        description: "",
        type: "choice",
        choice: JSON.stringify(["", "", "", ""]),
        answer: JSON.stringify({0: true}),
        bankId: this.exam.bankId,
      };
      axios({
        url: "question/addQuestion",
        data: question,
      })
          .then((res) => {
            if (res.data.errCode == 0) {
              question.id = res.data.data;
              question.choice = JSON.parse(question.choice);
              question.answer = JSON.parse(question.answer);
              this.questions["choice"][question.id] = question;
              if (!this.order["choice"]) this.order["choice"] = [];
              this.order["choice"].push(question.id);
              if (this.order.part.indexOf("choice") < 0) this.order.part.push("choice");

              this.questionScores[question.id] = {};
              this.questionScores[question.id].score = 2;
              this.getTitleNumberIndex();
              this.commitExam();
              this.currentTitleNumber =
                  this.titleNumberIndex.findIndex((val) => val.id == question.id) +
                  1;
              this.currentQueType = "choice";
              this.currentId = question.id;

              axios({
                url: "exam/addQuestionScore",
                data: {
                  questionId: question.id,
                  examId: this.examId,
                  score: this.questionScores[question.id].score,
                },
              })
                  .then((res) => {
                    if (res.data.errCode != 0) {
                      alert("修改失败");
                    }
                  })
                  .catch(() => {
                    alert("修改失败");
                  });
            } else {
              alert("添加失败");
            }
          })
          .catch((error) => {
            console.log(error);
            alert("添加失败");
          });
    },
    deleteQuestion() {
      let id = this.currentId;
      let type = this.currentQueType;
      axios({
        url: "question/deleteQuestion",
        data: {
          id: id,
        },
      })
          .then((res) => {
            if (res.data.errCode != 0) {
              alert("删除失败");
            } else {
              delete this.questions[type][id];
              delete this.questionScores[id];
              this.order[type].splice(this.order[type].indexOf(id), 1);
              if (this.order[type].length == 0) {
                this.order.part.splice(this.order.part.indexOf(type), 1);
              }
              let index = this.titleNumberIndex.findIndex((val) => val.id == id);
              this.titleNumberIndex.splice(index, 1);
              if (index >= this.titleNumberIndex.length)
                index = this.titleNumberIndex.length - 1;
              if (index >= 0) {
                this.currentId = this.titleNumberIndex[index].id;
                this.currentQueType = this.titleNumberIndex[index].type;
                this.titleNumber = index + 1;
              }

              this.commitExam();
              // this.getTitleNumberIndex();
            }
          })
          .catch(() => {
            alert("删除失败");
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
    fullMark() {
      let fullMark = 0;
      for (let id in this.questionScores) {
        fullMark += this.questionScores[id].score;
      }
      return fullMark;
    },
  },
  watch: {
    user() {
      if (this.user != null) {
        this.initExam();
      }
    },
    currentTitleNumber(val) {
      this.score =
          this.questionScores[this.currentId] &&
          this.questionScores[this.currentId].score;
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

.card :deep(.dropdown) {
  font-size: 12px;
}

.input {
  width: 237px;
  height: 28px;
  background: #e6e6e6;
  border-radius: 5px;
  font-size: 14px;
}

.order {
  color: #338afb;
  margin: 0 10px;
  cursor: pointer;
}
</style>
