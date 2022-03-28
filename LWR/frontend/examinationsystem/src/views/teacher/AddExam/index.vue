<template>
  <div class="container">

    <div class="head">
      <el-steps style="height: 70px;font-size: 19px" :space="200" :active="step" simple>
        <el-step @click="backTo(0)" style="cursor: pointer;" title="创建方式" :icon="EditPen"/>
        <el-step @click="backTo(1)" style="cursor: pointer;" title="试卷设置" :icon="Setting"/>
        <el-step @click="backTo(2)" style="cursor: pointer;" title="添加考生" :icon="User"/>
      </el-steps>
    </div>

    <div class="content">
      <div>
        <span class="tag-title">{{ title[step] }}</span>
        <el-icon color="#338AFB" :size="30" style="float: right">
          <Back @click="step--" style="cursor: pointer" v-show="step>0"/>
        </el-icon>
        <el-divider style="margin:10px 0"/>
      </div>
      <transition name="fade" mode="out-in">
        <component :form="form" @nextStep="nextStep" :is="tag">

        </component>
      </transition>

      <div style="width: 190px;margin:35px auto 0 auto">
        <el-button @click="nextStep" type="primary">{{ step &lt; 2 ? '下一步' : '编辑考题' }}</el-button>
        <el-button @click="cancel">取消</el-button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent, PropType} from "vue";
import ExamCreation from "@/views/teacher/AddExam/ExamCreation.vue";
import ExamSettings from "@/views/teacher/AddExam/ExamSettings.vue";
import AddExaminee from "@/views/teacher/AddExam/AddExaminee.vue";
import {ExamForm, ExamType, FormationType, Platform} from "@/views/teacher/AddExam/ExamConfigSetup";
import {ErrCode, Exam} from "@/models";
import {Response} from "@/models";
import axios from "axios";

export default defineComponent({
  components: {
    ExamCreation,
    ExamSettings,
    AddExaminee
  },
  data() {
    return {
      tag: 'ExamCreation',
      title: ['选择创建方式', '试卷设置', '添加考生'],
      form: {
        formationType: FormationType.Manual,
        title: "",
        subtitle: "",
        examType: ExamType.Fixed,
        year: "2019",
        platform: [],
        difficulty: "",
        time: [],
        totalScore: 0,
        antiCheat: false,
        durationMinute: 60,
        durationSecond: 0,
        repeatTime: 1,
        calGradeAtOnce: true
      } as ExamForm,
      examBefore: {} as Exam
    };
  },
  methods: {
    nextStep() {
      //完成编辑
      if (this.step >= 2) {
        this.$store.state.config.showLoading = true;
        //构建考试数据
        const examData = {
          id: undefined,
          title: this.form.title,
          subtitle: this.form.subtitle,
          earliestStartTime: new Date(this.form.time[0]).valueOf(),
          latestStartTime: new Date(this.form.time[1]).valueOf(),
          duration: (this.form.durationMinute * 60 + this.form.durationSecond) * 1000,
          bankId: undefined,
          type: this.form.examType == ExamType.Fixed ? 'fixed' : 'random',
          selectCountJson: undefined,
          orderJson: this.examBefore.orderJson ? this.examBefore.orderJson : '{"part": []}',
          repeatTime: this.form.repeatTime,
          calGradeAtOnce: this.form.calGradeAtOnce,
          fullMark: 0
        } as Exam;

        if (this.mode == 'add') {
          axios({
            url: "exam/addNewExam"
          }).then((res) => {
            if (res.data["errCode"] != 0) {
              alert("添加失败");
            } else {
              examData.id = res.data.data;
            }
            return axios({
              url: "exam/addExam",
              data: examData
            });
          }).then((res) => {
            if (res.data["errCode"] != 0) {
              alert("添加失败");
            } else {
              this.$router.replace("/teacher/examEdit/" + examData.id);
            }
          }).catch(() => {
            alert("添加失败");
          }).finally(() => {
            this.$store.state.config.showLoading = false;
          });
        } else if (this.mode == 'edit') {
          examData.id = Number.parseInt(this.$route.params['examId'] as string);
          axios({
            url: "exam/addExam",
            data: examData
          }).then((res) => {
            if (res.data["errCode"] != 0) {
              alert("添加失败");
            } else {
              this.$router.replace("/teacher/examEdit/" + examData.id);
            }
          }).catch(() => {
            alert("添加失败");
          }).finally(() => {
            this.$store.state.config.showLoading = false;
          });
        }

        return;
      }
      this.step++;

    },
    backTo(val: number) {
      if (this.mode == 'add') {
        if (val < this.step)
          this.step = val;
      } else {
        this.step = val;
      }
    },
    cancel() {
      if (this.mode == 'add')
        this.$router.push('/teacher/examManage');
      else if (this.mode == 'edit')
        this.$router.push('/teacher/examEdit/' + this.$route.params['examId']);
    },
    toForm(exam: Exam) {
      this.form = {
        formationType: FormationType.Manual,
        title: exam.title,
        subtitle: exam.subtitle,
        platform: [Platform.Mobile, Platform.Web],
        year: "2019",
        difficulty: "简单",
        time: [new Date(exam.earliestStartTime).toString(), new Date(exam.latestStartTime).toString()],
        totalScore: exam.fullMark,
        antiCheat: true,
        durationMinute: exam.duration / 1000 / 60,
        durationSecond: exam.duration / 1000 % 60,
        examType: exam.type == 'fixed' ? ExamType.Fixed : ExamType.Random,
        repeatTime: exam.repeatTime,
        calGradeAtOnce: exam.calGradeAtOnce
      };
    }
  },
  mounted() {
    if (this.mode == 'edit') {
      // console.log(JSON.parse(this.$route.params['exam'] as string));
      if (this.$route.params['exam']) {
        this.examBefore = JSON.parse(this.$route.params['exam'] as string);
        this.toForm(this.examBefore);
      } else {
        this.$store.state.config.showLoading = true;
        const examId = Number.parseInt(this.$route.params['examId'] as string);
        axios({
          url: "exam/getExams",
          data: {
            id: examId
          }
        }).then((res) => {
          const data: Response = res.data;
          if (data.errCode == ErrCode.SUCCESS) {
            this.examBefore = data.data.data[0];
            this.toForm(this.examBefore);
          } else {
            alert("获取失败！");
            this.$router.replace("/teacher/examEdit/" + Number.parseInt(this.$route.params['examId'] as string));
          }
        }).catch(() => {
          alert("获取失败！");
          this.$router.replace("/teacher/examEdit/" + Number.parseInt(this.$route.params['examId'] as string));
        }).finally(() => {
          this.$store.state.config.showLoading = false;
        });
      }
    }
  },
  computed: {
    step: {
      get(): number {
        if (this.tag == 'ExamCreation')
          return 0;
        else if (this.tag == 'ExamSettings')
          return 1;
        else
          return 2;
      },
      set(val: number) {
        if (val < 0)
          return;
        if (val == 0)
          this.tag = 'ExamCreation';
        else if (val == 1)
          this.tag = 'ExamSettings';
        else
          this.tag = 'AddExaminee';
      }
    }
  },
  props: {
    mode: {
      type: String as PropType<string>,
      default: 'add'
    }
  }
});

</script>

<script lang="ts" setup>
import {
  EditPen,
  Setting,
  User,
  Back
} from "@element-plus/icons-vue";
</script>

<style lang="scss" scoped>
.container {
  height: auto;
  width: 1000px;
  margin: auto;
  background-color: #fff;

  :deep(.head) {
    width: 100%;
    height: auto;
  }

  .content {
    padding: 20px;
    margin-bottom: 30px;
  }

  :deep(.tag-title) {
    font-size: 28px;
    font-family: SimHei, sans-serif;
    font-weight: normal;
    color: #232D50;
  }
}

.head {
  :deep(.el-step.is-simple .el-step__title) {
    font-size: 19px;
    font-family: Segoe UI, sans-serif;
    margin-left: 10px;
  }

  :deep(.el-step.is-simple .el-step__icon-inner) {
    font-size: 31px;
    position: relative;
    top: 4px;
  }
}
</style>
