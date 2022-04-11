<template>
  <div class="parent">
    <el-form label-width="120px">
      <el-form-item label="题库：">
        <el-select @change="bankId=choose;getQuestions()" v-model="choose" placeholder="Select" size="large">
          <el-option
              v-for="item in options"
              :key="item.key"
              :label="item.value"
              :value="item.key"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <el-transfer
        v-model="value"
        style="text-align: left; display: block"
        filterable
        :titles="['题库', '导入题目']"
        :format="{
        noChecked: '${total}',
        hasChecked: '${checked}/${total}',
      }"
        :data="data"
    >
      <template #default="{ option }">
        <span>{{ option.key }} - {{ option.label }}</span>
      </template>
    </el-transfer>
    <div style="margin-top: 20px">
      <el-button @click="onOkClick()" type="primary">确定</el-button>
      <el-button @click="$emit('cancelClick')">取消</el-button>
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {ErrCode, Pair} from "@/models";
import axios from "axios";
import {Response} from "@/models";
import {ElMessage} from "element-plus";

interface Option {
  key: number;
  label: string;
  disabled: boolean;
}

export default defineComponent({
  data() {
    return {
      options: [] as Pair[],
      choose: -1,
      value: [] as number[],
      data: [] as Option[],
      bankId: -1,
      questions: {} as { [index: number]: any }
    };
  },
  emits: ["cancelClick", "okClick"],
  methods: {
    getBanks() {
      if (!this.user)
        return;
      return axios({
        url: "/question/getBanks",
        data: {
          author: this.user.id
        }
      }).then((res) => {
        const data: Response = res.data;
        if (data.errCode != ErrCode.SUCCESS)
          throw new Error();
        this.options = [];
        for (const item of data.data.data) {
          this.options.push({key: item.id, value: item.name});
        }
        if (this.options[0]) {
          this.choose = Number.parseInt(this.options[0].key);
          this.bankId = Number.parseInt(this.options[0].key);
          this.getQuestions();
        } else
          this.choose = -1;
      }).catch(() => {
        ElMessage({message: "获取数据失败", type: "error"});
      });
    },
    getQuestions() {
      console.log(this.choose)
      // this.$store.state.config.showLoading=true;
      return axios({
        url: "/question/getAllQuestions",
        data: {
          bankId: this.bankId
        }
      }).then((res) => {
        const data: Response = res.data;
        if (data.errCode != ErrCode.SUCCESS)
          throw new Error();
        const questions = data.data;
        this.data = [];
        this.value = [];

        const regex = /<\/?.+?\/?>/gm;
        for (const question of questions.choice) {
          question.score = 2;
          this.questions[question.id] = question;
          this.data.push({
            key: question.id,
            label: question.description.replace(regex, "").replace(/&nbsp;/gi, ""),
            disabled: false
          });
        }
        for (const question of questions.normal) {
          question.score = 2;
          this.questions[question.id] = question;
          this.data.push({
            key: question.id,
            label: question.description.replace(regex, "").replace(/&nbsp;/gi, ""),
            disabled: false
          });
        }
      }).catch(() => {
        ElMessage({message: "获取数据失败", type: "error"});
      });
    },
    onOkClick() {
      const result: any[] = [];
      for (const id of this.value) {
        result.push(this.questions[id]);
      }
      this.$emit('okClick', result);
    }
  },
  computed: {
    user() {
      return this.$store.state.config.user;
    }
  },
  watch: {
    user() {
      this.getBanks();
    }
  },
  created() {
    this.getBanks();
  }
});
</script>

<style lang="scss" scoped>
.parent {
  text-align: center;
  background-color: white;
  width: fit-content;
  padding: 20px;

  &:deep(.el-transfer-panel) {
    width: 350px;
    height: 500px;
  }

  &:deep(.el-transfer-panel__body) {
    height: 100%;
  }

  &:deep(.el-transfer-panel__filter) {
    width: 90%;
  }
}
</style>
