<template>
  <table class="table" cellspacing="0">
    <tr>
      <th>标题1</th>
      <th>标题2</th>
      <th>标题3</th>
    </tr>
    <tr>
      <td>a</td>
      <td>b</td>
      <td>c</td>
    </tr>
    <tr>
      <td>a</td>
      <td>b</td>
      <td>c</td>
    </tr>
    <tr>
      <td>a</td>
      <td>b</td>
      <td>c</td>
    </tr>
  </table>
  <table class="footer">
    <tr>
      <td style="width: 33%; text-align: right">a</td>
      <td style="text-align: center; font-size: 0">
        <table class="page">
          <tr>
            <td><svg-icon iconName="left" className="arrow"></svg-icon></td>
            <td><span class="text">1</span></td>
            <td><svg-icon iconName="right" className="arrow"></svg-icon></td>
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
      order: "",
      desc: false,
    };
  },
  methods: {
    getItems() {
      axios({
        url: this.url,
        data: {
          offset: this.itemsPerPage * this.currentPage,
          max: this.itemsPerPage,
          order: this.order,
          desc: this.desc,
        },
      })
        .then((response) => {
          console.log(response.data);
        })
        .catch(() => {
          alert("请求数据失败！");
        });
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
    column: { type: Array, default: () => [] },
    itemsPerPage: { type: Number, default: 10 },
    currentPage: { type: Number, default: 1 },
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
</style>