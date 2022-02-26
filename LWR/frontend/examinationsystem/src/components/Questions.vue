<template>
  <div class="container1">
    <div class="classification">
      <div class="left">
        <div>
          <button class="BTN">单选题</button>
          <button class="BTN">多选题</button>
          <button class="BTN">填空题</button>
          <button class="BTN">判断题</button>
          <button class="BTN">简答题</button>
        </div>
      </div>
      <div class="right">
        <div style="text-align: right">
          <button class="BTN">智能导入</button>
          <button class="BTN">题库选题</button>
        </div>
      </div>
    </div>
    <div class="line"></div>
    <div class="question">
      <div class="score">
        <span>分数:</span>
        <input type="text" class="input-score" />
      </div>
      <div class="content">
        <div>题干:</div>
        <div style="margin-left: 30px">
          <WangEnduit
            class="edit"
            v-model="Content"
            :isClear="isClear"
            @change="change"
            :value="Content"
          />
        </div>
      </div>
    </div>
    <div class="line2"></div>
    <div class="anser">
      <div class="detail" style="display: flex">
        <div>选项:</div>
        <div style="color: red; margin-left: 30px">
          在当前页面选中的答案为本题正确答案
        </div>
      </div>
      <div style="display: flex; margin-top: 20px">
        <div class="anser-content">
          <input type="radio" name="rd" value="A" />A
        </div>
        <div style="margin-left: 34.87px">
          <WangEnduit
            v-model="Content"
            :isClear="isClear"
            @change="change"
            :value="Content"
          />
        </div>
      </div>
    </div>
    <div class="line2"></div>
    <div class="analysis">
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
    </div>
    <div class="line2"></div>
    <div class="difficulty">
      <div style="margin-right: 30px">难度:</div>
      <DropDown
        width=""
        height="30"
        className="drop"
        :values="dropDown"
        @change="onDropDownChange"
      ></DropDown>
    </div>
    <div class="line2"></div>
    <div class="knowledge">
      <div style="margin-right: 16px">知识点:</div>
      <DropDown
        width="200"
        height="30"
        className="drop"
        :values="dropDown"
        @change="onDropDownChange"
      ></DropDown>
    </div>
    <div class="footer">
      <button class="btn2" style="background-color: #5399f3; color: #fff;border: none">保存该题</button>
      <button class="btn2">重置</button>
    </div>

    <!-- <WangEnduit
      v-model="Content"
      :isClear="isClear"
      @change="change"
      :value="Content"
    /> -->
  </div>
</template>

<script>
import WangEnduit from "@/components/TextEdit.vue";
import DropDown from "@/components/DropDown.vue";
export default {
  components: {
    WangEnduit,
    DropDown,
  },
  data() {
    return {
      Content: "",
      isClear: false,
      dropDown: [
        { value: "hard", text: "难" },
        { value: "middle", text: "中等" },
        { value: "easy", text: "易" },
      ],
    };
  },
  methods: {
    change(val) {
      console.log("val", val);
    },
  },
};
</script>

<style scoped>
.container1 {
  width: 90%;
  background-color: #fff;
  border: solid 1px rgba(177, 177, 177, 0.466);
  box-shadow: 1px;
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
.BTN {
  height: 40px;
  width: 70px;
  border: solid rgb(202, 202, 202) 0.5px;
  border-radius: 5px;
  background-color: #fff;
  outline: none;
  cursor: pointer;
  margin-right: 10px;
}
.BTN:hover {
  background-color: #5399f3;
  color: #fff;
  border: none;
}
.btn2 {
  height: 40px;
  width: 100px;
  border-radius: 5px;
  background-color: #fff;
  border: solid rgb(202, 202, 202) 0.5px;
  margin-left: 5px;
  margin-right:5px;
  outline: none;
}
.line {
  width: 100%;
  height: 1px;
  border: solid 0.5px rgb(202, 202, 202);
}
.line2 {
  width: 100%;
  height: 1px;
  border: dashed 0.5px rgb(202, 202, 202);
}
.question {
  width: 100%;
  height: auto;
  padding: 20px;
}
.score {
  display: flex;
}
.input-score {
  height: 25px;
  width: 50px;
  border-radius: 5px;
  border: 1px solid rgb(202, 202, 202);
  text-align: center;
  outline: none;
  display: block;
  margin-left: 30px;
}
.content {
  width: 100%;
  margin-top: 20px;
  display: flex;
}
.anser {
  padding: 20px;
}
.anser-content {
  text-align: center;
  line-height: 248px;
}
.analysis {
  padding: 20px;
}
.difficulty {
  padding: 20px;
  display: flex;
}
.knowledge {
  padding: 20px;
  display: flex;
}
.footer {
  text-align: center;
  padding-top: 40px;
  padding-bottom: 40px;
}
</style>
