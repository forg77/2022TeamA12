<template>
  <div>
    <Card>
      <template v-slot:headerLeft> 考试列表 </template>
      <template v-slot:headerRight>
        <SearchBox></SearchBox>
        <button class="btn" style="margin-left: 30px">搜索考试</button>
      </template>
      <template v-slot:content>
        <div class="content">
          <button class="btn">,</button>
        </div>
      </template>
    </Card>
  </div>
</template>

<script>
import Card from "@/components/Card.vue";
import SearchBox from "@/components/SearchBox.vue";
export default {
  components: {
    Card,
    SearchBox,
  },
};
</script>
