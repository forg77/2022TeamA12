<template>
  <div style="width: 100%; height: 100%">
    <div class="container1">
      <div class="classification">
        <div class="left" style="width:100%">
          <div style="width:100%;display:flex">
            <button
              class="BTN"
              @click="tag = 'Choice'"
              :class="{ 'BTN-select': tag == 'Choice' }"
            >
              单选题
            </button>
            <button
              class="BTN"
              @click="tag = 'MultiChoice'"
              :class="{ 'BTN-select': tag == 'MultiChoice' }"
            >
              多选题
            </button>
            <!-- <button class="BTN">多选题</button> -->
            <button
              class="BTN"
              @click="tag = 'Completion'"
              :class="{ 'BTN-select': tag == 'Completion' }"
            >
              填空题
            </button>
            <button
              class="BTN"
              @click="tag = 'ShortAnswer'"
              :class="{ 'BTN-select': tag == 'ShortAnswer' }"
            >
              简答题
            </button>
            <button class="BTN" :class="{ 'BTN-select': tag == 'more' }">
              更多 >
            </button>

            
        </div>
        <div class="right">
          <div style="text-align: right">
            <!-- <button class="BTN">智能导入</button> -->
            <!-- <button class="BTN">题库选题</button> -->
            <el-upload
              class="upload-demo"
              style="margin-left:auto"
              :on-success="onUploadSuccess"
              action="https://jsonplaceholder.typicode.com/posts/"
              :show-file-list="false"
            >
              <el-button type="primary">上传图片</el-button></el-upload
            >
          </div>
          </div>
        </div>
      </div>
      <div class="line"></div>
      <div class="tag">
        <!-- <Choice></Choice> -->
        <transition name="fade" mode="out-in">
          <component
            ref="tag"
            :id="id"
            :bankId="bankId"
            :questionBefore="question"
            :is="tag"
            @save="onSaveQuestion"
            @saveDone="$emit('saveDone')"
          >
          </component>
        </transition>
      </div>

      <!-- <div class="line2"></div> -->
      <!-- <div class="analysis">
      <div style="margin-bottom: 20px">
        <input type="checkbox" />
        答案解析
      </div>
      <div style="margin-left: 66.67px; margin-right: 250.01px">
        <WangEnduit
          v-model="Content"
          :isClear="isClear"
          @change="change"
          :value="Content"
        />
      </div>
    </div> -->
      <!-- <div class="difficulty">
      <div style="margin-right: 30px">难度:</div>
      <DropDown
        width=""
        height="30"
        className="drop"
        :values="dropDown"
        @change="onDropDownChange"
      ></DropDown>
    </div> -->
      <!-- <div class="line2"></div> -->
      <!-- <div class="knowledge">
      <div style="margin-right: 16px">知识点:</div>
      <DropDown
        width="200"
        height="30"
        className="drop"
        :values="dropDown"
        @change="onDropDownChange"
      ></DropDown>
    </div> -->

      <!-- <WangEnduit
      v-model="Content"
      :isClear="isClear"
      @change="change"
      :value="Content"
    /> -->
    </div>
  </div>
</template>

<script>
import WangEnduit from "@/components/TextEdit";
import DropDown from "@/components/DropDown.vue";

import Choice from "./Choice.vue";
import Completion from "./Completion.vue";
import MultiChoice from "@/components/QuestionEdit/MultiChoice";
import ShortAnswer from "@/components/QuestionEdit/ShortAnswer";

import axios from "axios";
import { ElMessage } from 'element-plus';
export default {
  components: {
    WangEnduit,
    DropDown,
    Choice,
    Completion,
    MultiChoice,
    ShortAnswer,
  },
  emits: ["save", "saveDone"],
  data() {
    return {
      tag: "Choice",
      dropDown: [
        { value: "hard", text: "难" },
        { value: "middle", text: "中等" },
        { value: "easy", text: "易" },
      ],
      question: null,
    };
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
  methods: {
    getInfo() {
      axios({
        url: "question/getQuestion",
        data: {
          id: this.id,
        },
      }).then((res) => {
        this.question = res.data.data;
        this.tag = this.question.type;
        if (this.tag == "choice") {
          this.tag = "Choice";
          this.question.choice = JSON.parse(this.question.choice);
          this.question.answer = JSON.parse(this.question.answer);
        } else if (this.tag == "completion") {
          this.tag = "Completion";
          this.question.answer = JSON.parse(this.question.answer);
        } else if (this.tag == "multi_choice") {
          this.tag = "MultiChoice";
          this.question.choice = JSON.parse(this.question.choice);
          this.question.answer = JSON.parse(this.question.answer);
        } else if (this.tag == "short_answer") {
          this.tag = "ShortAnswer";
          this.question.answer = JSON.parse(this.question.answer);
        }
      });
    },
    initQuestion() {
      if (this.questionBefore == null) return;
      this.question = Object.assign({}, this.questionBefore);
      this.tag = this.question.type;
      if (this.tag == "choice") {
        this.tag = "Choice";
        // this.question.choice = JSON.parse(this.question.choice);
        // this.question.answer = JSON.parse(this.question.answer);
      } else if (this.tag == "completion") {
        this.tag = "Completion";
        // this.question.answer = JSON.parse(this.question.answer);
      } else if (this.tag == "multi_choice") {
        this.tag = "MultiChoice";
      } else if (this.tag == "short_answer") {
        this.tag = "ShortAnswer";
      }
    },
    onSaveQuestion(question) {
      this.$emit("save", question);
    },
    onUploadSuccess(){
      ElMessage({message:"上传成功",type:"success"});
      this.question={
        description:"一个社会中，经济制度和政治制度之间是 ()",
        answer:{0:true},
        type:"choice",
        choice:["决定与被决定的关系","相互对抗的关系","相互决定的关系","相互融合的关系"],
        bankId:this.bankId
      }
    }
  },
  mounted() {
    if (this.questionBefore == null && this.id) this.getInfo();
    this.initQuestion();
  },
  watch: {
    id(value) {
      if (value != null) this.getInfo();
      else {
        this.$refs.tag.reset();
      }
    },
    questionBefore() {
      this.initQuestion();
    },
  },
};
</script>

<style lang="scss" scoped>
@import "src/styles/variables";
.container1 {
  /* width: 1000px;
  height: 600px; */
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: #fff;
  border: solid 1px rgba(177, 177, 177, 0.466);
  //box-shadow: 1px;
  display: inline-block;
}
.classification {
  height: auto;
  padding: 20px;
  display: flex;
}
.left {
  height: 100%;
  width: 60%;
  display: flex;
}
.right {
  height: 100%;
  width: 40%;
}
.BTN,
.tag :deep(.BTN) {
  height: 40px;
  width: 70px;
  border: solid rgb(202, 202, 202) 0.5px;
  border-radius: 5px;
  background-color: #fff;
  outline: none;
  cursor: pointer;
  margin-right: 10px;
  transition: all 0.5s;
}
.BTN:hover,
.BTN-select,
.tag :deep(.BTN:hover) {
  background-color: $primary-color;
  color: #fff;
  border: none;
}

.btn2,
.tag :deep(.btn2) {
  height: 40px;
  width: 100px;
  border-radius: 5px;
  background-color: #fff;
  border: solid rgb(202, 202, 202) 0.5px;
  margin-left: 5px;
  margin-right: 5px;
  outline: none;
}
.line,
.tag :deep(.line) {
  width: 100%;
  height: 1px;
  border: solid 0.5px rgb(202, 202, 202);
}
.line2,
.tag :deep(.line2) {
  width: 100%;
  height: 1px;
  border: dashed 0.5px rgb(202, 202, 202);
}
.question,
.tag :deep(.question) {
  width: 100%;
  height: auto;
  padding: 20px;
}
.score,
.tag :deep(.score) {
  display: flex;
}
.input-score,
.tag :deep(.input-score) {
  height: 25px;
  width: 50px;
  border-radius: 5px;
  border: 1px solid rgb(202, 202, 202);
  text-align: center;
  outline: none;
  display: block;
  margin-left: 30px;
}
.content,
.tag :deep(.content) {
  width: 100%;
  margin-top: 20px;
  display: flex;
}
.anser,
.tag :deep(.anser) {
  padding: 20px;
}
/* .anser-content,
.tag :deep(.anser-content) {
  text-align: center;
  line-height: 248px;
} */
.analysis,
.tag :deep(.analysis) {
  padding: 20px;
}
.difficulty,
.tag :deep(.difficulty) {
  padding: 20px;
  display: flex;
}
.knowledge,
.tag :deep(.knowledge) {
  padding: 20px;
  display: flex;
}
.footer,
.tag :deep(.footer) {
  text-align: center;
  padding-top: 40px;
  padding-bottom: 40px;
}

:deep(.add) {
  cursor: pointer;
  color: $primary-color;
}
</style>
