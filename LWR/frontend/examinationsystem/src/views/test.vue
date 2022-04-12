<template>
  <div class="container">
    <el-button>dfsf</el-button>
    <div style="width: 600px;height:400px;" ref="main"></div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

import * as echarts from "echarts";

export default defineComponent({
  mounted() {
    const m=this.$refs.main as HTMLDivElement;
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(m);
    // 绘制图表
    myChart.setOption({
      title: {
        text: "ECharts 入门示例",
      },
      tooltip: {},
      xAxis: {
        data: ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"],
      },
      yAxis: {},
      series: [
        {
          name: "销量",
          type: "bar",
          data: [5, 20, 36, 10, 10, 20],
        },
      ],
    });
  },
});
</script>
<style lang="scss" scoped>
</style>