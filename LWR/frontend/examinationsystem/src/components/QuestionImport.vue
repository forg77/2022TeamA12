<template>
  <div class="parent" style="text-align: center">
    <el-transfer
      v-model="rightValue"
      style="text-align: left; display: inline-block"
      filterable
      :titles="['题库', '导入题目']"
      :format="{
        noChecked: '${total}',
        hasChecked: '${checked}/${total}',
      }"
      :data="data"
      @change="handleChange"
    >
      <template #default="{ option }">
        <span>{{ option.key }} - {{ option.label }}</span>
      </template>
    </el-transfer>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import type { VNode, VNodeProps } from "vue";

interface Option {
  key: number;
  label: string;
  disabled: boolean;
}

const generateData = (): Option[] => {
  const data: Option[] = [];
  for (let i = 1; i <= 15; i++) {
    data.push({
      key: i,
      label: `Option ${i}`,
      disabled: i % 4 === 0,
    });
  }
  return data;
};

const data = ref(generateData());
const rightValue = ref([1]);
const leftValue = ref([1]);

const renderFunc = (
  h: (type: string, props: VNodeProps | null, children?: string) => VNode,
  option: Option
) => {
  return h("span", null, option.label);
};
const handleChange = (
  value: number | string,
  direction: "left" | "right",
  movedKeys: string[] | number[]
) => {
  console.log(value, direction, movedKeys);
};
</script>

<style scoped>
.parent :deep(.el-transfer-panel) {
  width: 350px;
  height: 500px;
}
.parent :deep(.el-transfer-panel__body) {
  height: 100%;
}

</style>
