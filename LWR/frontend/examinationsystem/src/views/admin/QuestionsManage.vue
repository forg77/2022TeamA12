<template>
  <div>
    <Card>
      <template v-slot:headerLeft> 题库列表 </template>
      <template v-slot:headerRight>
        <table>
          <tr>
            <td style="font-size: 0"><SearchBox placeholder="按标题搜索"></SearchBox></td>
            <td>
              <button class="btn" style="margin-left: 30px">搜索题库</button>
            </td>
          </tr>
        </table>
      </template>
      <template v-slot:content>
        <div class="content">
          <Table url="test/test" :columns="columns"></Table>
        </div>
      </template>
    </Card>
  </div>
</template>

<script>
import Card from "@/components/Card.vue";
import SearchBox from "@/components/SearchBox.vue";
import Table from "@/components/Table.vue";
export default {
  data() {
    return {
      columns: [
        { title: "id", name: "id" },
        { title: "标题1", name: "a" },
        { title: "标题2", name: "b" },
        { title: "标题3", name: "c" },
      ],
    };
  },
  components: {
    Card,
    SearchBox,
    Table,
  },
};
</script>

<style scoped>
.content {
  padding: 20.5px;
}
</style>