<template>
  <div>
    <Card>
      <template v-slot:headerLeft> 消息列表 </template>
      <template v-slot:content>
        <div class="content">
          <div style="height: 20px"></div>

          <div class="box">
            <div class="img">
              <div class="image"></div>
            </div>
            <div class="MessageContent">
              <div class="Mtitle">标题</div>
              <div class="Mcontent">
                32321321..................................
              </div>
            </div>
            <div class="time">
              <div class="Time">
                <svg-icon iconName="time" className="icon"></svg-icon>&nbsp;
                2020-01-23
              </div>
            </div>
          </div>

          <div style="height: 1px"></div>
        </div>
      </template>
    </Card>
  </div>
</template>

<script>
import Card from "@/components/Card.vue";
import Svgicon from "../../components/Svgicon/index.vue";
export default {
  components: {
    Card,
    Svgicon,
  },
};
</script>

<style scoped>
.box {
  width: 960px;
  height: 90px;
  background: #eeeeee;
  opacity: 1;
  border-radius: 10px;
  margin: auto;
  margin-bottom: 20px;
  display: flex;
}
.img {
  height: auto;
  width: 15%;
  display: flex;
  justify-content: center;
}
.MessageContent {
  height: auto;
  width: 70%;
  padding: 10px;
}
.time {
  height: auto;
  width: 15%;
  position: relative;
}
.image {
  height: 80px;
  width: 80px;
  /* border: solid; */
  margin: auto;
  background-color: #fff;
}
.Mtitle {
  font-size: 20px;
  font-weight: bolder;
  margin-bottom: 5px;
}
.Time {
  position: absolute;
  bottom: 5px;
  color: #888888;
}

.icon {
  color: #338afb;
}
.Mcontent {
  color: #888888;
}
</style>