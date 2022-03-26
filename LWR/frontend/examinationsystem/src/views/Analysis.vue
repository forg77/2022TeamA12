<template>
  <div class="outer" v-if="true">
    <div style="margin-left: auto; margin-right: 30px">
      <Card :width="650" :headerHeight="64">
        <template v-slot:headerLeft>
          成绩分析
          <span style="font-size: 15px; color: #9aa2b5">平均分：</span>
          <span style="font-size: 15px; color: #4089ff">10 </span>
          <span style="font-size: 15px; color: #9aa2b5">方差：</span>
          <span style="font-size: 15px; color: #4089ff">0.8</span>
        </template>

        <template v-slot:headerRight>
          <button class="BTN">成绩分布</button><button class="BTN">全部</button>
        </template>

        <template v-slot:content>
          <div ref="grade" style="width: 100%; height: 400px"></div>
        </template>
      </Card>
      <div>
        <Card :width="650" :headerHeight="64">
          <template v-slot:headerLeft> 教学建议 </template>
          <template v-slot:content>
            <table
              style="
                width: 80%;
                margin-left: 30px;
                border-collapse: separate;
                border-spacing: 0px 20px;
              "
            >
              <tr>
                <td><div class="line" style="background-color: blue"></div></td>
                <td>
                  总体平均分：<span
                    >总体平均分与难度系数相匹配，主要集中在70分左右。</span
                  >
                </td>
              </tr>
              <tr>
                <td><div class="line" style="background-color: pink"></div></td>
                <td>方差：<span>方差大，适当提供复习/习题课</span></td>
              </tr>
              <tr>
                <td><div class="line" style="background-color: red"></div></td>
                <td>
                  知识点平均分：<span
                    >部分题目正确率较低，适当加强XXX知识点的讲解</span
                  >
                </td>
              </tr>
              <tr>
                <td>
                  <div class="line" style="background-color: green"></div>
                </td>
                <td>总结：<span>造化钟神秀</span></td>
              </tr>
            </table>
          </template>
        </Card>
      </div>
    </div>
    <div>
      <Card :width="750" style="margin-right: auto" :headerHeight="64">
        <template v-slot:headerLeft> 知识点 </template>
        <template v-slot:headerRight>
          <button class="BTN" style="width: 200px">
            科目:马克思主义基本原理概论</button
          ><button class="BTN">分类:领域</button>
        </template>
        <template v-slot:content>
          <div ref="point1" style="width: 32%; height: 200px"></div>
          <div ref="point2" style="width: 32%; height: 200px"></div>
        </template>
      </Card>
      <div>
        <Card :width="750" style="margin-right: auto" :headerHeight="64">
          <template v-slot:headerLeft> 成绩详情 </template>
          <template v-slot:content>
            <div style="display: flex">
              <div>
                <span>班级/年级平均分</span>
                <br />
                <span>77.5</span>
                <br />
                <span>总分</span>
              </div>
              <div ref="Gauge" style="width: 40%; height: 250px"></div>
              <div>
                <div> 试卷难度系数： </div>
                <div> 班级/年级最高： </div>
                <div> 班级/年级最高： </div>
                <div> 班级/年级方差： </div>
              </div>
            </div>
          </template>
        </Card>
      </div>
    </div>
  </div>
  <div class="dialog" v-if="false">
    <Card :width="750" style="margin-right: auto" :headerHeight="64">
      <template v-slot:headerLeft> 考试列表 </template>
      <template v-slot:headerRight>
        <Dropdown></Dropdown>
        <Search></Search>
        <button class="search">搜索考试</button>
      </template>
      <template v-slot:content>
        <table
          style="border-collapse: separate; border-spacing: 40px 10px"
          frame="below"
        >
          <tr>
            <th>序号</th>
            <th>考试名称</th>
            <th>考试状态</th>
            <th>参与人数</th>
            <th>考试时间</th>
          </tr>
          <tr>
            <td><input type="checkbox" />1</td>
            <td>马克思主义基本原理概论</td>
            <td>已结束</td>
            <td>800</td>
            <td>2020-01-25</td>
          </tr>
        </table>
      </template>
    </Card>
  </div>
</template>

<script lang="ts">
import Card from "@/components/Card.vue";
import Search from "@/components/SearchBox.vue";
import Dropdown from "@/components/DropDown.vue";
import { defineComponent } from "vue";
import AccountManage from "@/views/Personal/AccountManage.vue";
import BasicInfo from "@/views/Personal/BasicInfo.vue";
import * as echarts from "echarts";

export default defineComponent({
  components: { Card, BasicInfo, AccountManage, Search, Dropdown },
  data() {
    return {
      content: "AccountManage",
    };
  },
  mounted() {
    const g = this.$refs.grade as HTMLDivElement;
    const p1 = this.$refs.point1 as HTMLDivElement;
    const G = this.$refs.Gauge as HTMLDivElement;
    const p2 = this.$refs.point2 as HTMLDivElement;

    // 基于准备好的dom，初始化echarts实例
    var gradeChart = echarts.init(g);
    var pointChart = echarts.init(p1);
    var GaugeChart = echarts.init(G);

    // 绘制图表
    pointChart.setOption({
      tooltip: {
        trigger: "item",
      },
      legend: {
        top: "5%",
        left: "center",
      },
      series: [
        {
          name: "Access From",
          type: "pie",
          radius: ["40%", "70%"],
          avoidLabelOverlap: false,
          label: {
            show: false,
            position: "center",
          },
          emphasis: {
            label: {
              show: true,
              fontSize: "40",
              fontWeight: "bold",
            },
          },
          labelLine: {
            show: false,
          },
          data: [
            { value: 1048, name: "Search Engine" },
            { value: 735, name: "Direct" },
            { value: 580, name: "Email" },
            { value: 484, name: "Union Ads" },
            { value: 300, name: "Video Ads" },
          ],
        },
      ],
    });
    gradeChart.setOption({
      tooltip: {
        trigger: "axis",
        axisPointer: {
          type: "shadow",
        },
      },
      grid: {
        left: "3%",
        right: "4%",
        bottom: "3%",
        containLabel: true,
      },
      xAxis: [
        {
          type: "category",
          data: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
          axisTick: {
            alignWithLabel: true,
          },
        },
      ],
      yAxis: [
        {
          type: "value",
        },
      ],
      series: [
        {
          name: "Direct",
          type: "bar",
          barWidth: "60%",
          data: [10, 52, 200, 334, 390, 330, 220],
        },
      ],
    });
    GaugeChart.setOption({
      series: [
        {
          type: "gauge",
          progress: {
            show: true,
            width: 18,
          },
          axisLine: {
            lineStyle: {
              width: 18,
            },
          },
          axisTick: {
            show: false,
          },
          splitLine: {
            length: 15,
            lineStyle: {
              width: 2,
              color: "#999",
            },
          },
          axisLabel: {
            distance: 25,
            color: "#999",
            fontSize: 10,
          },
          anchor: {
            show: true,
            showAbove: true,
            size: 10,
            itemStyle: {
              borderWidth: 10,
            },
          },
          title: {
            show: false,
          },
          detail: {
            valueAnimation: true,
            fontSize: 10,
            offsetCenter: [0, "70%"],
          },
          data: [
            {
              value: 70,
            },
          ],
        },
      ],
    });
  },
});
</script>
<style lang="scss" scoped>
.outer {
  display: flex;
}
.switch {
  width: 68px;
  height: 30px;
  background: #409eff;
  opacity: 1;
  border-radius: 4px;
  color: white;
  border: none;
  outline: none;
}
.BTN {
  width: 80px;
  height: 32px;
  border: 0.5px solid #4089ff;
  opacity: 1;
  border-radius: 0px;
  background-color: #fff;
  color: #9aa2b5;
  outline: none;
}
.BTN:hover {
  background-color: #e1edf6;
}
.line {
  height: 20px;
  width: 8px;
}
.dialog {
  width: 800px;
  height: 683px;
  background: #ffffff;
  border: 1px solid #707070;
  opacity: 1;
  border-radius: 0px;
}
.search {
  width: 120px;
  height: 40px;
  background: #338afb;
  opacity: 1;
  border-radius: 5px;
  border: none;
  color: white;
  outline: none;
}
</style>
