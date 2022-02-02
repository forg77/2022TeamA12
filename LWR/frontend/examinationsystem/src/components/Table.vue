<template>
  <table class="table" cellspacing="0">
    <tr>
      <th v-for="column in columns" :key="column.name">
        {{ column.title }}
      </th>
    </tr>
    <tr v-for="(item, index) in items" :key="index">
      <td v-for="column in columns" :key="column.name">
        {{ item[column.name] }}
      </td>
    </tr>
  </table>
  <table class="footer">
    <tr>
      <td style="width: 33%; text-align: right">a</td>
      <td style="text-align: center; font-size: 0">
        <table class="page">
          <tr>
            <td
              class="active"
              :class="{ disable: currentPage <= 1 }"
              @click="gotoPage(currentPage - 1)"
            >
              <svg-icon iconName="left" className="arrow"></svg-icon>
            </td>
            <td>
              <span class="text">{{ currentPage }}</span>
            </td>
            <td
              class="active"
              :class="{
                disable: currentPage >= getTotalPages(),
              }"
              @click="gotoPage(currentPage + 1)"
            >
              <svg-icon iconName="right" className="arrow"></svg-icon>
            </td>
          </tr>
        </table>
      </td>
      <td style="width: 33%">c</td>
    </tr>
  </table>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      items: [],
      totalCount: 0,
      order: null,
      desc: false,
      itemsPerPage: 3,
      currentPage: 1,
    };
  },
  methods: {
    getItems() {
      axios({
        url: this.url,
        data: {
          offset: this.itemsPerPage * (this.currentPage - 1),
          max: this.itemsPerPage,
          order: this.order,
          desc: this.desc,
        },
      })
        .then((response) => {
          this.totalCount = response.data.count;
          this.items = response.data.data;
        })
        .catch(() => {
          alert("请求数据失败！");
        });
    },
    getTotalPages() {
      let totalPages = Math.floor(this.totalCount / this.itemsPerPage);
      if (this.totalCount % this.itemsPerPage != 0) totalPages++;
      return totalPages;
    },
    gotoPage(page) {
      let newPage = this.currentPage;

      if (page <= 0) newPage = 1;
      else {
        let totalPages = this.getTotalPages();
        if (page > totalPages) newPage = totalPages;
        else newPage = page;
      }

      if (newPage != this.currentPage) {
        this.currentPage = newPage;
        this.getItems();
      }
    },
  },
  watch: {
    url() {
      this.getItems();
    },
  },
  created() {
    this.getItems();
  },
  props: {
    url: { type: String, default: "" },
    //对象格式{title:"title",name:"name",transformer:(data)=>data}
    columns: { type: Array, default: () => [] },
  },
};
</script>


<style scoped>
.table {
  width: 100%;
}

.table th {
  font-size: 20px;
  text-align: left;
  font-family: Microsoft YaHei;
  color: #000000;

  border-bottom-style: solid;
  border-color: #00000033;
  border-bottom-width: 1px;
  height: 60px;
}

.table td {
  font-size: 18px;
  font-family: Microsoft YaHei;
  color: #010101;
  opacity: 0.7;

  border-bottom-style: solid;
  border-color: #00000033;
  border-bottom-width: 1px;
  height: 60px;
}

.footer {
  width: 100%;
  margin-top: 35px;
}

.page {
  display: inline-block;
}

.page .text {
  font-size: 16px;
  font-family: Microsoft YaHei;
  color: #ff3c3c;
  opacity: 0.7;
  margin: 0 20px;
}

.page .arrow {
  width: 18px;
  height: 18px;
}

.page .active {
  cursor: pointer;
}

.page .disable {
  opacity: 0.2;
  cursor: default;
}
</style>