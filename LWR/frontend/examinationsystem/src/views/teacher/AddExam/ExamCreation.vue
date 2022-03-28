<template>
  <div>
    <div class="content1" style="margin: auto; width: fit-content">
      <el-radio style="margin-bottom: 20px;margin-top: 40px" v-model="form.formationType" :label="0" size="large">
        <span style="font-size: 25px">手动组卷（手动编辑创建新新试卷）</span>
      </el-radio>
      <br/>
      <el-radio style="margin-bottom: 60px" v-model="form.formationType" :label="1" size="large">
        <span style="font-size: 25px">自动随机组卷（系统从已有题库中随机选择题组卷）</span>
      </el-radio>
    </div>
    <br/>
<!--    <div style="width: 190px; margin: auto">-->
<!--      <el-button @click="$emit('nextStep')" type="primary" size="large">下一步</el-button>-->
<!--      <el-button size="large">取消</el-button>-->
<!--    </div>-->
  </div>
</template>

<script lang="ts">
import {defineComponent, PropType} from "vue";
import {Back} from "@element-plus/icons-vue";
import {ExamForm} from "@/views/teacher/AddExam/ExamConfigSetup";

export default defineComponent({
  name: "Creation",
  components: {
    Back
  },
  emits: ['nextStep'],
  data() {
    return {
      radio: "manual"
    };
  },
  props: {
    form: {
      type: Object as PropType<ExamForm>,
      required: true
    }
  }
});
</script>

<style lang="scss" scoped>
.content1 {
  :deep(.el-radio__inner) {
    width: 25px;
    height: 25px;

    &::after {
      width: 8px;
      height: 8px;
      border-radius: var(--el-radio-input-border-radius);
      background-color: var(--el-color-white);
      content: "";
      position: absolute;
      left: 50%;
      top: 50%;
      //transform: translate(-50%,-50%) scale(0);
      transition: transform .15s ease-in;
    }
  }
}
</style>
