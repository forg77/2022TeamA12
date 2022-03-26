<template>
  <div class="container">
    <div class="head">
      <el-steps :space="200" :active="1" simple>
        <el-step title="创建方式" :icon="EditPen" />
        <el-step title="试卷设置" :icon="Setting" />
        <el-step title="添加考生" :icon="User" />
      </el-steps>
    </div>
    <div class="content1" v-if="false">
      <div>
        选择创建方式
        <el-icon color="#338AFB" :size="30" style="float: right"
          ><Back
        /></el-icon>
      </div>
      <el-divider />
      <div style="margin: auto; width: 345px">
        <el-radio v-model="radio1" label="1" size="large"
          >手动组卷（手动编辑创建新新试卷）</el-radio
        >
        <br />
        <el-radio v-model="radio1" label="2" size="large"
          >自动随机组卷（系统从已有题库中随机选择题组卷）</el-radio
        >
      </div>
      <br />
      <div style="width: 190px; margin: auto">
        <el-button type="primary">立即创建</el-button>
        <el-button>取消</el-button>
      </div>
    </div>

    <div class="content2" v-if="true">
      <div>
        试卷设置
        <el-icon color="#338AFB" :size="30" style="float: right"
          ><Back
        /></el-icon>
      </div>
      <el-divider />
      <div>
        <el-form :model="form1" label-width="120px">
          <el-form-item label="标题：">
            <el-input
              v-model="form1.title"
              clearable
              style="width: 400px"
              placeholder="请输入试卷的标题"
            />
          </el-form-item>
          <el-form-item label="学年：">
            <el-select v-model="form1.year" placeholder="选择学年">
              <el-option label="2019" value="2019" />
              <el-option label="2020" value="2020" />
            </el-select>
          </el-form-item>
          <el-form-item label="平台：">
            <el-checkbox-group v-model="form1.checklist">
              <el-checkbox label="移动端" />
              <el-checkbox label="WEB端" />
            </el-checkbox-group>
          </el-form-item>
          <el-form-item label="难度：">
            <el-select v-model="form1.difficulty" placeholder="选择难度">
              <el-option label="简单" value="easy" />
              <el-option label="中等" value="normal" />
              <el-option label="困难" value="difficult" />
            </el-select>
          </el-form-item>
          <el-form-item label="时间：">
            <el-date-picker
              v-model="form1.time"
              type="datetimerange"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
            />
            <span>
              <el-icon color="#338AFB" :size="20"><Timer /></el-icon>
              共计：100分钟 30秒</span
            >
          </el-form-item>
          <el-form-item label="总分：">
            <el-input
              v-model="form1.totalscore"
              clearable
              style="width: 200px"
              placeholder="请输入试卷的总分"
            />
          </el-form-item>
          <el-form-item label="作弊：">
            <el-switch v-model="form1.switch" /><span>&nbsp;开启反作弊</span>
          </el-form-item>
        </el-form>
      </div>

      <div style="margin-left: 60px; margin-right: 60px">
        <el-card shadow="never">
          <template #header>
            <div>
              <span>单选题 </span>
              <el-icon><Delete /></el-icon>
            </div>
          </template>
          <div>
            <el-form :model="choice">
              <el-form-item label="总分：">
                <el-input clearable style="width: 200px" v-model="choice.total" />
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
                  >从题库指定目录选题</el-radio
                >
              </el-form-item>
              <el-form-item>
                <el-radio v-model="choice.select" label="3" size="large"
                  >从题库按照难以度选题</el-radio
                >
              </el-form-item>
            </el-form>
          </div>
        </el-card>
        <br />
        <el-card shadow="never">
          <template #header>
            <div>
              <span>填空题 </span>
              <el-icon><Delete /></el-icon>
            </div>
          </template>
          <div>
            <el-form :model="fill">
              <el-form-item label="总分：">
                <el-input clearable style="width: 200px" v-model="fill.total" />
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
                  >从题库指定目录选题</el-radio
                >
              </el-form-item>
              <el-form-item>
                <el-radio v-model="fill.select" label="3" size="large"
                  >从题库按照难以度选题</el-radio
                >
              </el-form-item>
            </el-form>
          </div>
        </el-card>
      </div>

      <div style="width: 190px; margin: auto">
        <el-button type="primary">立即创建</el-button>
        <el-button>取消</el-button>
      </div>
    </div>

    <div class="content3" v-if="false">
      <div>
        添加考生
        <el-icon color="#338AFB" :size="30" style="float: right"
          ><Back
        /></el-icon>
      </div>
      <el-divider />
    </div>
  </div>
</template>


<script lang="ts" setup>
import {
  EditPen,
  Setting,
  User,
  Back,
  Timer,
  Delete,
} from "@element-plus/icons-vue";
import { ref } from "vue";
import { reactive } from "vue";

const radio1 = ref("1");
const form1 = reactive({
  title: "",
  year: "",
  checklist: [],
  difficulty: "",
  time: "",
  totalscore: "",
  switch: false,
});
const fill = reactive({
  total: "",
  describe: "",
  select: "",
  number: "",
});

const choice = reactive({
  total: "",
  describe: "",
  select: "",
  number: "",
});
</script>

<style>
.container {
  height: auto;
  width: 800px;
  margin: auto;
  background-color: #fff;
}
.head {
  width: 100%;
  height: auto;
}
.content1,
.content2,
.content3,
.content4 {
  padding: 20px;
}
</style>