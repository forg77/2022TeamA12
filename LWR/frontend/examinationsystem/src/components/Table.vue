<template>
  <div
    id="scroll"
    style="max-height: 1000px; overflow-x: hidden; min-height: 189px"
    :style="{ 'overflow-y': overflowY }"
  >
    <div :style="{ height: totalHeight + 'px' }">
      <table class="table" cellspacing="0">
        <tr class="row">
          <th
            v-for="column in columns"
            :key="column.name"
            @click="setOrderColumn(column.name)"
          >
            {{ column.title }}
            <template v-if="column.name == order">
              <svg-icon iconName="ascending" v-show="!desc"></svg-icon>
              <svg-icon iconName="descending" v-show="desc"></svg-icon>
            </template>
          </th>
          <th v-if="canEdit"></th>
        </tr>
        <transition-group name="list-complete">
          <tr
            v-for="item in items"
            :key="item.id"
            class="row list-complete-item"
          >
            <td
              class="item"
              v-for="column in columns"
              :key="column.name"
              :style="{ height: lineHeight }"
            >
              <template v-if="editingId != item.id">
                {{ item[column.name] }}
              </template>
              <template v-else>
                <input
                  v-if="column.editable!==false"
                  type="text"
                  class="inputBox"
                  v-model="editItems[column.name]"
                />
                <template v-else>
                  {{ item[column.name] }}
                </template>
              </template>
            </td>
            <td
              v-if="canEdit"
              class="item edit"
              style="width: 60px"
              :class="{ show: item.id == editingId }"
            >
              <table style="width: 100%; text-align: center">
                <tr>
                  <template v-if="item.id != editingId">
                    <td style="cursor: pointer" @click="setEditingId(item)">
                      <svg-icon iconName="edit"></svg-icon></td
                  ></template>
                  <template v-else>
                    <td style="cursor: pointer" @click="updateItem()">
                      <svg-icon iconName="correct"></svg-icon>
                    </td>
                    <td style="cursor: pointer" @click="setEditingId(-1)">
                      <svg-icon iconName="wrong"></svg-icon>
                    </td>
                  </template>
                </tr>
              </table>
            </td>
          </tr>
        </transition-group>
      </table>

      <div
        style="text-align: center; width: 100%"
        :style="{
          height: (totalHeight < 1000 ? totalHeight : 1000) - 43 + 'px',
        }"
        v-show="loading || loadFailed"
      >
        <table style="width: 100%; height: 100%">
          <transition name="fade1">
            <tr v-show="loading">
              <td><Loading></Loading></td>
            </tr>
          </transition>
          <transition name="fade1">
            <tr v-show="loadFailed">
              <td>
                加载失败<br /><button
                  class="btn"
                  style="margin: 20px"
                  @click="getItems()"
                >
                  重新加载
                </button>
              </td>
            </tr>
          </transition>
        </table>
      </div>
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
import Loading from "./Loading.vue";

export default {
  components: {
    Loading,
  },
  data() {
    return {
      items: [],
      totalCount: 0,
      order: null,
      desc: false,
      itemsPerPage: 10,
      currentPage: 1,
      lineHeight: 60,
      totalHeight: 252,
      overflowY: "auto",
      timeout: null,
      loading: false,
      ajaxCancel: null,
      loadFailed: false,

      canEdit: true,
      editingId: -1,
      editItems: {},
    };
  },
  methods: {
    getItems() {
      if (this.ajaxCancel != null) {
        this.ajaxCancel();
        this.ajaxCancel = null;
      }
      this.loadFailed = false;
      let scroll = document.getElementById("scroll");
      scroll.scrollTop = 0;

      let start = new Date();
      this.overflowY = "hidden";
      if (this.timeout != null) clearTimeout(this.timeout);
      this.items = [];
      let loadingTimeout = setTimeout(() => {
        this.loading = true;
      }, 900);

      axios({
        url: this.url,
        cancelToken: new axios.CancelToken((c) => {
          this.ajaxCancel = c;
        }),
        data: {
          offset: this.itemsPerPage * (this.currentPage - 1),
          max: this.itemsPerPage,
          order: this.order,
          desc: this.desc,
        },
      })
        .then((response) => {
          this.ajaxCancel = null;
          this.totalHeight =
            (this.lineHeight + 3) * (1 + response.data.data.length);
          let interval = new Date() - start;
          // clearTimeout(loadTimeout);

          // if (interval > 800) {
          //   clearTimeout(loadingTimeout);
          //   this.loading = false;
          //   setTimeout(() => {
          //     this.totalCount = response.data.count;
          //     this.items = response.data.data;

          //     if (this.timeout != null) clearTimeout(this.timeout);
          //     this.timeout = setTimeout(() => {
          //       this.overflowY = "auto";
          //       this.timeout = null;
          //     }, 900);
          //   }, 1100);
          // } else {
          setTimeout(() => {
            clearTimeout(loadingTimeout);
            let waitTime = 0;
            if (this.loading == true) {
              this.loading = false;
              waitTime = 1100;
            }
            setTimeout(() => {
              this.totalCount = response.data.count;
              this.items = response.data.data;

              if (this.timeout != null) clearTimeout(this.timeout);
              this.timeout = setTimeout(() => {
                this.overflowY = "auto";
                this.timeout = null;
              }, 900);
            }, waitTime);
          }, 800 - interval);
          // }
        })
        .catch((error) => {
          clearTimeout(loadingTimeout);
          if (error.response || error.request) {
            // alert("请求数据失败！");
            let waitTime = 0;

            if (this.loading == true) {
              this.loading = false;
              waitTime = 1100;
            }
            setTimeout(() => {
              this.loadFailed = true;
            }, waitTime);
          }
        });
      this.isAjaxing = true;
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
        if (totalPages == 0) newPage = 1;
        else if (page > totalPages) newPage = totalPages;
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
        if (totalPages == 0) target.value = 1;
        else if (target.value > totalPages) target.value = totalPages;
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
        let totalPages = this.getTotalPages();
        if (this.currentPage > totalPages) this.currentPage = totalPages;
        this.getItems();
      }
    },
    setOrderColumn(name) {
      if (this.order == name) {
        this.desc = !this.desc;
      } else {
        this.order = name;
        this.desc = false;
      }

      this.getItems();
    },
    setEditingId(item) {
      if (item != -1) {
        this.editingId = item.id;
        for (let col of this.columns) {
          this.editItems[col.name] = item[col.name];
        }
      } else this.editingId = -1;
    },
    updateItem() {
      let data = {};
      data.id = this.editingId;
      for (let col of this.columns) {
        data[col.name] = this.editItems[col.name];
      }
      axios({
        url: this.updateUrl,
        data: data,
      })
        .then((response) => {
          if (response.data["errorCode"] == 0) {
            this.getItems();
            this.editingId = -1;
          } else alert("修改失败！");
        })
        .catch(() => {
          alert("连接失败！");
        });
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
  mounted() {
    this.getItems();
  },
  props: {
    url: { type: String, default: "" },
    updateUrl: { type: String, default: "" },
    //对象格式{title:"title",name:"name",transformer:(data)=>data,editable:true}
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

  cursor: pointer;
}

.table td.item {
  font-size: 18px;
  font-family: Microsoft YaHei;
  color: #010101;
  opacity: 0.7;

  border-bottom-style: solid;
  border-color: #00000033;
  border-bottom-width: 1px;
  height: 60px;
}

.table tr.row {
  transition: all 0.5s;
}

.table tr.row:hover {
  font-size: 21px;
  /* font-weight: bold; */
  transform: translateY(-5px);
  background: #eeeeee;
}

.table tr.row .edit {
  color: #00000000;
  transition: color 0.5s;
}

.table tr.row:hover .edit {
  color: #000000ff;
}

.table tr.row .show {
  color: #000000ff;
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

.fade1-enter-active {
  transition: opacity 1s ease;
}
.fade1-leave-active {
  /* display: none; */
  transition: opacity 1s ease;
}

.fade1-enter-from,
.fade1-leave-to {
  opacity: 0;
}
</style>