<template>
  <div>
    <!-- 确认删除对话框 -->
    <DialogBox v-model:show="showDeleteDialog">
      <template v-slot:header>确认</template>
      确定删除所选项吗？
      <template v-slot:bottom>
        <button
          class="btn"
          @click="
            deleteItems();
            showDeleteDialog = false;
          "
          style="margin-right: 20px"
        >
          确定
        </button>
        <button class="btn" @click="showDeleteDialog = false">取消</button>
      </template>
    </DialogBox>
    <!-- 操作失败对话框 -->
    <DialogBox v-model:show="showFailedDialog">
      <template v-slot:header>错误</template>
      {{ failedDialogMessage }}
      <template v-slot:bottom>
        <button class="btn" @click="showFailedDialog = false">确定</button>
      </template>
    </DialogBox>
    <button v-if="Boolean(urls.addUrl)" class="btn" @click="setAdding()">
      {{ isAdding ? addString.cancel : addString.add }}
    </button>
    <button
      v-if="Boolean(urls.deleteUrl)"
      class="btn"
      @click="showDeleteDialog = true"
      style="width: 150px; margin-left: 20px"
      :disabled="selectCount <= 0"
    >
      删除所选项{{ selectCount > 0 ? "(" + selectCount + ")" : "" }}
    </button>
    <button
      v-if="config.canSelect"
      class="btn"
      @click="selectItems = {}"
      :disabled="selectCount <= 0"
      style="margin-left: 20px"
    >
      取消选择
    </button>
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
            <th v-if="Boolean(urls.updateUrl)" style="cursor: default"></th>
          </tr>
          <transition-group name="list-complete">
            <tr
              v-for="item in items"
              :key="item.id"
              class="row list-complete-item"
              :style="{
                cursor:
                  config.canSelect || config.canClick ? 'pointer' : 'default',
              }"
              :class="{ select: item.id in selectItems }"
              @click="
                selectItem(item);
                clickItem(item);
              "
            >
              <td
                class="item"
                v-for="column in columns"
                :key="column.name"
                :style="{ height: lineHeight }"
                 style="max-width:200px;overflow-x:hidden;text-overflow:ellipsis;white-space:nowrap;"
              >
                <template v-if="editingId != item.id">
                  {{
                    column.transformer
                      ? column.transformer(item[column.name])
                      : item[column.name]
                  }}
                </template>
                <template v-else-if="item.id != -2 || column.name != 'id'">
                  <input
                    v-if="column.editable !== false"
                    type="text"
                    class="inputBox"
                    v-model="editItems[column.name]"
                    @click="$event.stopPropagation()"
                  />
                  <template v-else>
                    {{
                      column.transformer
                        ? column.transformer(item[coflumn.name])
                        : item[column.name]
                    }}
                  </template>
                </template>
              </td>
              <td
                v-if="Boolean(urls.updateUrl) || isAdding"
                class="item edit"
                style="width: 60px"
                :class="{ show: item.id == editingId }"
              >
                <table style="width: 100%; text-align: center">
                  <tr>
                    <template v-if="item.id != editingId">
                      <td
                        style="cursor: pointer"
                        @click="
                          setEditingId(item);
                          $event.stopPropagation();
                        "
                      >
                        <svg-icon iconName="edit"></svg-icon></td
                    ></template>
                    <template v-else>
                      <td
                        style="cursor: pointer"
                        @click="
                          updateItem();
                          $event.stopPropagation();
                        "
                      >
                        <svg-icon iconName="correct"></svg-icon>
                      </td>
                      <td
                        style="cursor: pointer"
                        @click="
                          setEditingId(-1);
                          $event.stopPropagation();
                        "
                      >
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
  </div>
</template>

<script>
import axios from "axios";
import Loading from "./Loading.vue";
import DialogBox from "./DialogBox.vue";

export default {
  components: {
    Loading,
    DialogBox,
  },
  data() {
    return {
      items: [],
      totalCount: 0,
      order: null,
      desc: false,
      itemsPerPage: 8,
      currentPage: 1,
      lineHeight: 60,
      totalHeight: 252,
      overflowY: "auto",
      timeout: null,
      loading: false,
      ajaxCancel: null,
      loadFailed: false,

      // canEdit: true,
      editingId: -1,
      editItems: {},

      // canAdd: true,
      isAdding: false,
      addString: { add: "添加", cancel: "取消" },
      loadComplete: false,

      selectItems: {},

      // canDelete: true,

      showDeleteDialog: false,
      showFailedDialog: false,
      failedDialogMessage: "操作失败，请检查输入是否正确",
    };
  },
  methods: {
    getItems() {
      this.loadComplete = false;
      if (this.ajaxCancel != null) {
        this.ajaxCancel();
        this.ajaxCancel = null;
      }
      this.isAdding = false;
      this.loadFailed = false;
      let scroll = document.getElementById("scroll");
      scroll.scrollTop = 0;

      let start = new Date();
      this.overflowY = "hidden";
      if (this.timeout != null) clearTimeout(this.timeout);
      this.items = [];
      let loadingTimeout = setTimeout(() => {
        this.loading = true;
      }, 1100);

      let data = {
        offset: this.itemsPerPage * (this.currentPage - 1),
        max: this.itemsPerPage,
        order: this.order,
        desc: this.desc,
      };
      for (let pro in this.extraData) {
        data[pro] = this.extraData[pro];
      }
      
      axios({
        url: this.urls.queryUrl,
        cancelToken: new axios.CancelToken((c) => {
          this.ajaxCancel = c;
        }),
        data: data,
      })
        .then((response) => {
          this.ajaxCancel = null;
          this.totalHeight =
            (this.lineHeight + 3) * (1 + response.data.data.data.length);
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
              this.totalCount = response.data.data.count;
              this.items = response.data.data.data;
              this.loadComplete = true;

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
      if (this.isAdding) {
        this.items.shift();
        this.isAdding = false;
      }
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
        url: this.isAdding ? this.urls.addUrl : this.urls.updateUrl,
        data: data,
      })
        .then((response) => {
          if (response.data["errorCode"] == 0) {
            this.getItems();
            this.editingId = -1;
          } else {
            if (!this.isAdding)
              this.failedDialogMessage = "修改失败，请检查输入是否正确";
            else this.failedDialogMessage = "添加失败，请检查输入是否正确";
            this.showFailedDialog = true;
          }
        })
        .catch(() => {
          this.failedDialogMessage = "连接失败，请检查网络连接";
          this.showFailedDialog = true;
        });
    },
    setAdding() {
      if (!this.loadComplete) return;
      if (!this.isAdding) {
        this.isAdding = true;
        this.editingId = -2;
        this.editItems = {};
        this.items.unshift({ id: -2 });
      } else {
        this.isAdding = false;
        this.editingId = -1;
        this.items.shift();
      }
    },
    selectItem(item) {
      if (!this.config.canSelect || item.id == -2) return;
      if (item.id in this.selectItems) delete this.selectItems[item.id];
      else {
        if (!this.config.canMultiSelect) {
          this.selectItems = {};
        }
        this.selectItems[item.id] = item;
      }
    },
    deleteItems() {
      let data = { itemsId: [] };
      for (let id in this.selectItems) {
        data.itemsId.push(id);
      }
      axios({
        url: this.urls.deleteUrl,
        data: data,
      })
        .then((response) => {
          if (response.data["errorCode"] == 0) {
            this.getItems();
            this.editingId = -1;
            this.selectItems = {};
          } else {
            this.failedDialogMessage = "删除失败";
            this.showFailedDialog = true;
          }
        })
        .catch(() => {
          this.failedDialogMessage = "连接失败，请检查网络连接";
          this.showFailedDialog = true;
        });
    },
    clickItem(item) {
      if (!this.config.canClick) return;
      this.$emit("clickItem", item);
    },
  },
  emits: ["clickItem"],
  computed: {
    selectCount() {
      return Object.keys(this.selectItems).length;
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
  },
  mounted() {
    if (this.config.getItemsOnCreate || this.config.getItemsOnCreate == null)
      this.getItems();
  },
  props: {
    // url: { type: String, default: "" },
    // updateUrl: { type: String, default: "" },
    // addUrl: { type: String, default: "" },
    // deleteUrl: { type: String, default: "" },
    //对象格式{title:"title",name:"name",transformer:(data)=>data,editable:true}
    columns: { type: Array, default: () => [] },
    urls: {
      type: Object,
      default() {
        return {
          queryUrl: "",
          updateUrl: "",
          addUrl: "",
          deleteUrl: "",
        };
      },
    },
    config: {
      type: Object,
      default() {
        return {
          canSelect: false,
          canMultiSelect: true,
          canClick: false,
          getItemsOnCreate: true,
        };
      },
    },
    extraData: {
      type: Object,
      default() {
        return {};
      },
    },
    // canSelect: { type: Boolean, default: false },
    // canMultiSelect: { type: Boolean, default: true },
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
  /* font-size: 21px; */
  /* font-weight: bold; */
  transform: translateY(-5px);
  background-color: #eeeeee;
}

.table tr.row .edit {
  color: #00000000;
  transition: color 0.5s;
}

.table tr.select {
  background-color: #dddddd;
}

.table tr.select:hover {
  background-color: #cccccc;
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
  color: #338AFB;
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

  border-radius: 5px;
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