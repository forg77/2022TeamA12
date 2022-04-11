<template>
  <div>
    <div class="outer" v-if="true">
      <div style="margin-left: auto; margin-right: 30px">
        <Card :width="650" :headerHeight="64">
          <template v-slot:headerLeft>
            成绩分析
            <span style="font-size: 15px; color: #9aa2b5">平均分：</span>
            <span style="font-size: 15px; color: #4089ff">77 </span>
            <span style="font-size: 15px; color: #9aa2b5">方差：</span>
            <span style="font-size: 15px; color: #4089ff">0.8</span>
          </template>

          <template v-slot:headerRight>
            <button class="BTN">成绩分布</button>
            <button class="BTN">全部</button>
          </template>

          <template v-slot:content>
            <div ref="grade" style="width: 100%; height: 400px"></div>
          </template>
        </Card>
        <div>
          <Card :width="650" :headerHeight="64">
            <template v-slot:headerLeft> 教学建议</template>
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
                  <td>
                    <div class="line" style="background-color: #8EA1F0;"></div>
                  </td>
                  <td>
                    总体平均分：<span
                      >总体平均分与难度系数相匹配，主要集中在70分左右。</span
                    >
                  </td>
                </tr>
                <tr>
                  <td>
                    <div class="line" style="background-color: #82C7EB;"></div>
                  </td>
                  <td>方差：<span>方差大，适当提供复习/习题课</span></td>
                </tr>
                <tr>
                  <td>
                    <div class="line" style="background-color: #A5F0E4;"></div>
                  </td>
                  <td>
                    知识点平均分：<span
                      >部分题目正确率较低，适当加强XXX知识点的讲解</span
                    >
                  </td>
                </tr>
                <tr>
                  <td>
                    <div class="line" style="background-color: #F5FAC8;"></div>
                  </td>
                  <td>总结：<span>造化钟神秀</span></td>
                </tr>
              </table>
            </template>
          </Card>
        </div>
      </div>
      <div style="margin-right: 28px">
        <Card :width="750" style="margin-right: auto" :headerHeight="64">
          <template v-slot:headerLeft> 知识点</template>
          <template v-slot:headerRight>
            <button class="BTN" style="width: 200px">
              科目:马克思主义基本原理概论
            </button>
            <button class="BTN">分类:领域</button>
          </template>
          <template v-slot:content>
            <div style="display: flex">
              <div style="width: 32%">
                <div ref="point1" style="height: 200px"></div>
                <div style="text-align: center">知识点1</div>
              </div>
              <div style="width: 32%">
                <div ref="point2" style="height: 200px"></div>
                <div style="text-align: center">知识点2</div>
              </div>
              <div style="width: 32%">
                <div ref="point3" style="height: 200px"></div>
                <div style="text-align: center">知识点3</div>
              </div>
            </div>
            <div style="display: flex">
              <div style="width: 32%">
                <div ref="point4" style="height: 200px"></div>
                <div style="text-align: center">知识点4</div>
              </div>
              <div style="width: 32%">
                <div ref="point5" style="height: 200px"></div>
                <div style="text-align: center">知识点5</div>
              </div>
              <div style="width: 32%">
                <div ref="point6" style="height: 200px"></div>
                <div style="text-align: center">知识点6</div>
              </div>
            </div>
            <br />
          </template>
        </Card>
        <div>
          <Card :width="750" style="margin-right: auto" :headerHeight="64">
            <template v-slot:headerLeft> 成绩详情</template>
            <template v-slot:content>
              <div style="display: flex">
                <div style="width: 25%; text-align: right; padding-top: 60px">
                  <div style="color: #9aa2b5; font-size: 14px">
                    班级/年级平均分
                  </div>
                  <br />
                  <div
                    style="font-size: 22px; font-weight: bold; color: #5470c6"
                  >
                    77.5
                  </div>
                  <br />
                  <div style="color: #9aa2b5; font-size: 14px">总分</div>
                </div>
                <div ref="Gauge" style="width: 40%; height: 250px"></div>
                <div style="padding-top: 40px">
                  <div style="color: #9aa2b5; font-size: 14px">
                    试卷难度系数：<span style="color: #338afb">0.8</span>
                  </div>
                  <br />
                  <div style="color: #9aa2b5; font-size: 14px">
                    班级/年级最高：<span style="color: #338afb">95/98</span>
                  </div>
                  <br />
                  <div style="color: #9aa2b5; font-size: 14px">
                    班级/年级最高：<span style="color: #338afb">32/25</span>
                  </div>
                  <br />
                  <div style="color: #9aa2b5; font-size: 14px">
                    班级/年级方差：<span style="color: #338afb">15/13</span>
                  </div>
                </div>
              </div>
            </template>
          </Card>
        </div>
      </div>
    </div>
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
    const p3 = this.$refs.point3 as HTMLDivElement;
    const p4 = this.$refs.point4 as HTMLDivElement;
    const p5 = this.$refs.point5 as HTMLDivElement;
    const p6 = this.$refs.point6 as HTMLDivElement;


    // 基于准备好的dom，初始化echarts实例
    var gradeChart = echarts.init(g);
    var pointChart1 = echarts.init(p1);
    var pointChart2 = echarts.init(p2);
    var pointChart3 = echarts.init(p3);
    var pointChart4 = echarts.init(p4);
    var pointChart5 = echarts.init(p5);
    var pointChart6 = echarts.init(p6);

    var GaugeChart = echarts.init(G);

    // 绘制图表
    pointChart1.setOption({
      tooltip: {
        trigger: "item",
      },
      legend: {
        top: "5%",
        left: "center",
      },
      series: [
        {
          type: "pie",
          radius: ["40%", "70%"],
          avoidLabelOverlap: false,
          label: {
            show: false,
            position: "center",
          },
          labelLine: {
            show: false,
          },
          data: [
            { value: 484, itemStyle: { color: "rgb(145,204,117)" } },
            { value: 300, itemStyle: { color: "rgb(238,102,102)" } },
          ],
        },
      ],
    });
    pointChart2.setOption({
      tooltip: {
        trigger: "item",
      },
      legend: {
        top: "5%",
        left: "center",
      },
      series: [
        {
          type: "pie",
          radius: ["40%", "70%"],
          avoidLabelOverlap: false,
          label: {
            show: false,
            position: "center",
          },
          labelLine: {
            show: false,
          },
          data: [
            { value: 100, itemStyle: { color: "rgb(145,204,117)" } },
            { value: 200, itemStyle: { color: "rgb(238,102,102)" } },
          ],
        },
      ],
    });
    pointChart3.setOption({
      tooltip: {
        trigger: "item",
      },
      legend: {
        top: "5%",
        left: "center",
      },
      series: [
        {
          type: "pie",
          radius: ["40%", "70%"],
          avoidLabelOverlap: false,
          label: {
            show: false,
            position: "center",
          },
          labelLine: {
            show: false,
          },
          data: [
            { value: 100, itemStyle: { color: "rgb(145,204,117)" } },
            { value: 100, itemStyle: { color: "rgb(238,102,102)" } },
          ],
        },
      ],
    });
    pointChart4.setOption({
      tooltip: {
        trigger: "item",
      },
      legend: {
        top: "5%",
        left: "center",
      },
      series: [
        {
          type: "pie",
          radius: ["40%", "70%"],
          avoidLabelOverlap: false,
          label: {
            show: false,
            position: "center",
          },
          labelLine: {
            show: false,
          },
          data: [
            { value: 20, itemStyle: { color: "rgb(145,204,117)" } },
            { value: 30, itemStyle: { color: "rgb(238,102,102)" } },
          ],
        },
      ],
    });
    pointChart5.setOption({
      tooltip: {
        trigger: "item",
      },
      legend: {
        top: "5%",
        left: "center",
      },
      series: [
        {
          type: "pie",
          radius: ["40%", "70%"],
          avoidLabelOverlap: false,
          label: {
            show: false,
            position: "center",
          },
          labelLine: {
            show: false,
          },
          data: [
            { value: 40, itemStyle: { color: "rgb(145,204,117)" } },
            { value: 70, itemStyle: { color: "rgb(238,102,102)" } },
          ],
        },
      ],
    });
    pointChart6.setOption({
      tooltip: {
        trigger: "item",
      },
      legend: {
        top: "5%",
        left: "center",
      },
      series: [
        {
          type: "pie",
          radius: ["40%", "70%"],
          avoidLabelOverlap: false,
          label: {
            show: false,
            position: "center",
          },
          labelLine: {
            show: false,
          },
          data: [
            { value: 1, itemStyle: { color: "rgb(145,204,117)" } },
            { value: 5, itemStyle: { color: "rgb(238,102,102)" } },
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
          data: ["0-20", "20-40", "40-50", "50-60", "60-70", "70-80", "80-100"],
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
  width: 30px;
  margin-right:3px
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
