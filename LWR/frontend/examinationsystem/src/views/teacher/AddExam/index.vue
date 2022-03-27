<template>
  <div class="container">

    <div class="head">
      <el-steps style="height: 70px;font-size: 19px" :space="200" :active="step" simple>
        <el-step @click="backTo(0)" style="cursor: pointer;" title="创建方式" :icon="EditPen"/>
        <el-step @click="backTo(1)" style="cursor: pointer;" title="试卷设置" :icon="Setting"/>
        <el-step style="cursor: pointer;" title="添加考生" :icon="User"/>
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
      <transition :examConfig="config" name="fade" mode="out-in">
        <component @nextStep="nextStep" :is="tag">

        </component>
      </transition>
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import ExamCreation from "@/views/teacher/AddExam/ExamCreation.vue";
import ExamSettings from "@/views/teacher/AddExam/ExamSettings.vue";
import AddExaminee from "@/views/teacher/AddExam/AddExaminee.vue";
import {ExamConfig, FormationType} from "@/views/teacher/AddExam/ExamConfigSetup";

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
      config: {
        formationType: FormationType.Manual
      } as ExamConfig
    };
  },
  methods: {
    nextStep() {
      this.step++;
    },
    backTo(val: number) {
      if (val < this.step)
        this.step = val;
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
