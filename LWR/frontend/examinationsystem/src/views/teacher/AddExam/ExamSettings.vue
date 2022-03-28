<template>
  <div style="margin-top: 65px">
    <div class="form">
      <el-form label-width="140px" :model="form">
        <el-form-item label="标题：">
          <el-input
              v-model="form.title"
              clearable
              placeholder="请输入试卷的标题"
              input-style="width:660px"
          />
        </el-form-item>
        <el-form-item label="全名：">
          <el-input
              v-model="form.subtitle"
              clearable
              placeholder="请输入试卷的全名"
              input-style="width:660px"
          />
        </el-form-item>
        <el-form-item label="类型：">
          <el-select v-model="form.examType" placeholder="选择类型">
            <el-option label="固定题" :value="0"/>
            <el-option label="随机抽题" :value="1"/>
          </el-select>
        </el-form-item>
        <el-form-item label="学年：">
          <el-select v-model="form.year" placeholder="选择学年">
            <el-option label="2019" value="2019"/>
            <el-option label="2020" value="2020"/>
          </el-select>
        </el-form-item>
        <el-form-item label="平台：">
          <el-checkbox-group v-model="form.platform">
            <el-checkbox :label="0">移动端</el-checkbox>
            <el-checkbox :label="1">WEB端</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="难度：">
          <el-select v-model="form.difficulty" placeholder="选择难度">
            <el-option label="简单" value="easy"/>
            <el-option label="中等" value="normal"/>
            <el-option label="困难" value="difficult"/>
          </el-select>
        </el-form-item>
        <el-form-item label="时间范围：">
          <el-date-picker
              v-model="form.time"
              type="datetimerange"
              start-placeholder="最早开始时间"
              end-placeholder="最晚开始时间"
              style="width: 500px"
          />
          <el-tooltip>
            <el-icon class="question-icon" :size="25">
              <question-filled/>
            </el-icon>
            <template #content>允许学生在考试开始后的一段时间（即容错时间）内加入考试，考试时间与设定相同</template>
          </el-tooltip>
          <!--              <el-icon color="#338AFB" :size="20"><Timer/></el-icon>-->

          <!--          <span class="total">共计：<span class="time-highlight">&nbsp;{{ Math.floor(duration / 1000 / 60) }}&nbsp;</span>分钟<span-->
          <!--              class="time-highlight">&nbsp;{{ Math.floor(duration / 1000) % 60 }}&nbsp;</span>秒</span>-->
        </el-form-item>
        <el-form-item label="考试时间：">
          <svg-icon className="icon" iconName="sandglass"></svg-icon>
          <el-input
              v-model.number="form.durationMinute"
              clearable
              placeholder="分钟"
              input-style="width:100px"
          />
          <span class="total" style="margin: 0 10px">分钟</span>
          <el-input
              v-model.number="form.durationSecond"
              clearable
              placeholder="秒"
              input-style="width:100px"
          />
          <span class="total" style="margin: 0 10px">秒</span>
        </el-form-item>
        <el-form-item v-show="showAuto" label="总分：">
          <el-input
              v-model="form.totalScore"
              clearable
              style="width: 200px"
              placeholder="请输入试卷的总分"
          />
        </el-form-item>
        <el-form-item label="作弊：">
          <el-switch v-model="form.antiCheat"/>
          <span style="font-size: 24px;color:#606266;margin-left: 6px">&nbsp;开启反作弊</span>
        </el-form-item>
      </el-form>
    </div>

    <div v-show="showAuto" style="margin-left: 60px; margin-right: 60px">
      <el-card shadow="never">
        <template #header>
          <div>
            <span>单选题 </span>
            <el-icon>
              <Delete/>
            </el-icon>
          </div>
        </template>
        <div>
          <el-form :model="choice">
            <el-form-item label="总分：">
              <el-input clearable style="width: 200px" v-model="choice.total"/>
            </el-form-item>
            <el-form-item label="题型说明：">
              <el-input
                  clearable
                  style="width: 172px"
                  v-model="choice.describe"
              />
            </el-form-item>
            <el-form-item>
              <el-radio v-model="choice.select" label="1" size="large"
              >从题库选题
                <span>
                    共 999+ 道 抽
                    <input
                        type="number"
                        style="
                        border-radius: 5px;
                        width: 30px;
                        border: solid rgb(220, 223, 230) 0.7px;
                        outline: none;
                        height: 25px;
                      "
                        v-model="choice.number"
                    />
                    道
                  </span>
              </el-radio>
            </el-form-item>
            <el-form-item>
              <el-radio v-model="choice.select" label="2" size="large"
              >从题库指定目录选题
              </el-radio
              >
            </el-form-item>
            <el-form-item>
              <el-radio v-model="choice.select" label="3" size="large"
              >从题库按照难以度选题
              </el-radio
              >
            </el-form-item>
          </el-form>
        </div>
      </el-card>
      <br/>
      <el-card shadow="never">
        <template #header>
          <div>
            <span>填空题 </span>
            <el-icon>
              <Delete/>
            </el-icon>
          </div>
        </template>
        <div>
          <el-form :model="fill">
            <el-form-item label="总分：">
              <el-input clearable style="width: 200px" v-model="fill.total"/>
            </el-form-item>
            <el-form-item label="题型说明：">
              <el-input
                  clearable
                  style="width: 172px"
                  v-model="fill.describe"
              />
            </el-form-item>
            <el-form-item>
              <el-radio v-model="fill.select" label="1" size="large"
              >从题库选题
                <span>
                    共 999+ 道 抽
                    <input
                        type="number"
                        style="
                        border-radius: 5px;
                        width: 30px;
                        border: solid rgb(220, 223, 230) 0.7px;
                        outline: none;
                        height: 25px;
                      "
                        v-model="fill.number"
                    />
                    道
                  </span>
              </el-radio>
            </el-form-item>
            <el-form-item>
              <el-radio v-model="fill.select" label="2" size="large"
              >从题库指定目录选题
              </el-radio
              >
            </el-form-item>
            <el-form-item>
              <el-radio v-model="fill.select" label="3" size="large"
              >从题库按照难以度选题
              </el-radio
              >
            </el-form-item>
          </el-form>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script lang="ts" setup>
import {Delete, QuestionFilled} from "@element-plus/icons-vue";
import SvgIcon from "@/components/Svgicon/index.vue";
</script>

<script lang="ts">
import {defineComponent, PropType} from "vue";
import {ExamForm, ExamType, FormationType} from "@/views/teacher/AddExam/ExamConfigSetup";

export default defineComponent({
  name: "Creation",
  data() {
    return {
      // form1: {
      //   title: "",
      //   subtitle: "",
      //   examType: ExamType.Fixed,
      //   year: "2019",
      //   checklist: [],
      //   difficulty: "",
      //   time: [] as string[],
      //   totalscore: "",
      //   switch: false,
      //   durationMinute: 60,
      //   durationSecond: 0
      // },
      fill: {
        total: "",
        describe: "",
        select: "",
        number: "",
      },
      choice: {
        total: "",
        describe: "",
        select: "",
        number: "",
      }
    };
  },
  emits: ['nextStep'],
  props: {
    form: {
      type: Object as PropType<ExamForm>,
      required: true
    },
  },
  computed: {
    showAuto() {
      return this.form.formationType == FormationType.Auto;
    },
    // duration() {
    //   const start = new Date(this.form1.time[0]);
    //   const end = new Date(this.form1.time[1]);
    //   if (!start.valueOf() || !end.valueOf())
    //     return 0;
    //   return end.valueOf() - start.valueOf();
    // }
  },
  // watch: {
  //   form1: {
  //     handler(val) {
  //       console.log(val);
  //     },
  //     deep: true
  //   }
  // }
});
</script>

<style lang="scss" scoped>
@import "src/styles/variables";

.form {
  padding: 0 20px;

  :deep(.el-form-item__label) {
    font-size: 24px;
    font-family: Microsoft YaHei, serif;
    color: black;
    vertical-align: middle;
  }

  :deep(.el-input__inner) {
    height: 55px;
    vertical-align: middle;
    font-size: 19px;
  }

  :deep(.el-form-item) {
    align-items: center;
  }

  :deep(.el-form-item--default) {
    margin-bottom: 25px;
  }

  :deep(.el-checkbox__label) {
    font-size: 23px;
  }

  :deep(.el-checkbox-group) {
    display: flex;
  }

  :deep(.el-checkbox__inner) {
    width: 24px;
    height: 24px;

    &::after {
      left: 7px;
      width: 6px;
      height: 14px;
      border-width: 2px;
    }
  }

  :deep(.el-range-input) {
    font-size: 19px;
  }
}

.icon {
  color: $primary-color;
  width: 28px;
  height: 28px;
  margin-right: 20px;
}

.question-icon {
  margin-left: 10px;
  color: $primary-color;
}

.total {
  font-size: 20px;
}

.time-highlight {
  color: $primary-color;
}
</style>
