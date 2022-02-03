<template>
  <div
    style="max-height: 1000px; overflow-x: hidden"
    :style="{ 'overflow-y': overflowY }"
  >
    <div :style="{ height: totalHeight + 'px' }">
      <table class="table" cellspacing="0">
        <tr>
          <th v-for="column in columns" :key="column.name">
            {{ column.title }}
          </th>
        </tr>
        <transition-group name="list-complete">
          <tr v-for="item in items" :key="item.id" class="list-complete-item">
            <td
              v-for="column in columns"
              :key="column.name"
              :style="{ height: lineHeight }"
            >
              {{ item[column.name] }}
            </td>
          </tr>
        </transition-group>
      </table>
    </div>
  </div>
  <table class="footer">
    <tr>
      <td style="width: 44%; text-align: right">
        <span class="normal-text"
          >共{{ getTotalPages() }}页，共{{ totalCount }}条</span
        >

        <input
          id="setItemsPerPage"
          style="margin-left: 30px"
          class="inputBox input"
          type="number"
          :value="itemsPerPage"
          @input="onItemsPerPageChanged($event)"
        />
        <span class="normal-text" style="margin-right: 30px">条/页</span>
      </td>
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
      <td style="width: 44%; text-align: center">
        <span class="normal-text">前往</span
        ><input
          id="setPage"
          class="inputBox input"
          type="number"
          @input="onCurrentPageChanged($event)"
          :value="currentPage"
          style="-moz-appearance: textfield"
        /><span class="normal-text">页</span>
      </td>
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
      lineHeight: 60,
      totalHeight: 60,
      overflowY: "auto",
      timeout: null,
    };
  },
  methods: {
    getItems() {
      let start = new Date();
      this.overflowY = "hidden";
      if (this.timeout != null) clearTimeout(this.timeout);
      this.items = [];
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
          this.totalHeight =
            (this.lineHeight + 3) * (1 + response.data.data.length);
          let interval = new Date() - start;
          if (interval > 800) {
            this.totalCount = response.data.count;
            this.items = response.data.data;

            if (this.timeout != null) clearTimeout(this.timeout);
            this.timeout = setTimeout(() => {
              this.overflowY = "auto";
              this.timeout = null;
            }, 900);
          } else {
            setTimeout(() => {
              this.totalCount = response.data.count;
              this.items = response.data.data;
              if (this.timeout != null) clearTimeout(this.timeout);
              this.timeout = setTimeout(() => {
                this.overflowY = "auto";
                this.timeout = null;
              }, 900);
            }, 800 - interval);
          }
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
    onCurrentPageChanged(event) {
      let target = event.target;

      if (target.value <= 0) target.value = 1;
      else {
        let totalPages = this.getTotalPages();
        if (target.value > totalPages) target.value = totalPages;
      }
      if (target.value != this.currentPage) {
        this.currentPage = target.value;
        this.getItems();
      }
    },
    onItemsPerPageChanged(event) {
      let target = event.target;

      if (target.value <= 0) target.value = 1;
      else if (target.value > 100) target.value = 100;
      if (target.value != this.itemsPerPage) {
        this.itemsPerPage = target.value;
        this.getItems();
      }
    },
  },
  watch: {
    url() {
      this.getItems();
    },
    currentPage(value) {
      let element = document.getElementById("setPage");
      element.value = value;
    },
    itemsPerPage(value) {
      let element = document.getElementById("setItemsPerPage");
      element.value = value;
    },
    // currentPage(newValue, oldValue) {
    //   if (newValue <= 0) {
    //     this.currentPage = 1;
    //     return;
    //   } else {
    //     let totalPages = this.getTotalPages();
    //     if (newValue > totalPages) {
    //       this.currentPage = totalPages;
    //       return;
    //     }
    //   }
    //   if (oldValue != this.currentPage) this.getItems();
    // },
    // itemsPerPage(newValue, oldValue) {
    //   if (newValue <= 0) {
    //     this.itemsPerPage = 1;
    //     return;
    //   } else if (newValue > 100) {
    //     this.itemsPerPage = 100;
    //     return;
    //   }
    //   if (oldValue != this.itemsPerPage) {
    //     this.getItems();
    //     let totalPages = this.getTotalPages();
    //     if (this.currentPage > totalPages) this.currentPage = totalPages;
    //   }
    // },
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
  /* height: 800px; */
}

.table th {
  font-size: 20px;
  text-align: left;
  font-family: Microsoft YaHei;
  font-weight: 400;
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

.footer .normal-text {
  font-size: 16px;
  font-family: Microsoft YaHei;
  font-weight: 400;

  opacity: 0.7;
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

.input {
  width: 59px;
  height: 28px;
  margin: 0 4px;
  text-align: center;

  font-size: 16px;
  font-family: Microsoft YaHei;
  font-weight: 400;
  line-height: 0px;

  color: #010101b2;
}
</style>