<template>
  <div class="home">
    <Card>
      <template v-slot:headerLeft> 标题 </template>
      <template v-slot:content>
        <div class="content">
          <img alt="Vue logo" src="../assets/logo.png" />
          <HelloWorld msg="Welcome to Your Vue.js App" />
        </div>
      </template>
    </Card>
    <exam-card>
      <template v-slot:title>马克思主义基本原理概论</template>
      <template v-slot:subtitle>马克思主义基本原理概论-2019年上期末考试</template>
      <template v-slot:time>2020-01-23</template>
      <template v-slot:limitTime>限时120分钟</template>
      <template v-slot:score>满分100</template>
    </exam-card>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from "@/components/HelloWorld.vue";
import Card from "@/components/Card.vue";
import ExamCard from "../components/ExamCard.vue";

export default {
  name: "Home",
  components: {
    HelloWorld,
    Card,
    ExamCard,
  },
};
</script>

<style scoped>
.content {
  text-align: center;
  padding: 30px;
}
</style>