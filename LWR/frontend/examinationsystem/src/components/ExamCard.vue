<template>
  <div class="card-container" :class="[containerClass]" @click="this.$emit('click')">
    <div class="tag" v-show="tag&&tag!=''">
      {{ tag }}
    </div>
    <div class="head">
      <div class="exam-name">
        <slot name="title"></slot>
      </div>
      <button @click="onManageClick($event)" style="width: 64px;height: 31px;font-size: 16px;"
              :class="{btn:canManage,button:!canManage}">{{ canManage ? "管理" : "考试" }}
      </button>
    </div>

    <div class="content">
      <slot name="subtitle"></slot>
    </div>

    <table style="width: 428px" class="footer">
      <tbody>
      <td>
        <div class="time">
          <svg-icon iconName="calendar" className="icon"></svg-icon
          >&nbsp;<slot name="time"></slot>
        </div>
      </td>
      <td>
        <div class="limit-time">
          <svg-icon iconName="time" className="icon"></svg-icon
          >&nbsp;<slot name="limitTime"></slot>
        </div>
      </td>
      <td>
        <div class="score">
          <svg-icon iconName="100" className="icon"></svg-icon
          >&nbsp;<slot name="score"></slot>
        </div>
      </td>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  data() {
    return {}
  },
  props: ["containerClass", "tag", "canManage"],
  emits: ["click", "manageClick"],
  methods: {
    onManageClick(event) {
      if (this.canManage) {
        event.stopPropagation();
        this.$emit("manageClick",event);
      }
    }
  }
};
</script>


<style scoped>
.head {
  display: flex;
  height: auto;
  align-items: center;
  justify-content: space-between;
}

.card-container {
  width: 470px;
  height: 220px;
  background: #ffffff;
  border: 1px solid rgba(0, 0, 0, 0.10196078431372549);
  /* box-shadow: 2px -3px 6px rgba(0, 0, 0, 0.1); */
  opacity: 1;
  border-radius: 10px;
  padding: 40px 39px 0 20px;
  position: relative;
  display: inline-block;

  margin: 20px 10px;
  cursor: pointer;

  transition: transform 0.5s, box-shadow 0.5s;

  vertical-align: middle;
}

.card-container:hover {
  /* transform: scale(1.001); */
  box-shadow: 0px 0px 10px rgba(51, 138, 251, 0.6);
}

.exam-name {
  width: 264px;
  height: 31px;
  font-size: 24px;
  font-family: Microsoft YaHei;
  font-weight: bold;
  color: #000000;
  opacity: 1;
}

.button {
  /*display: flex;*/
  width: 64px;
  height: 31px;
  background: #338AFB;
  opacity: 1;
  border-radius: 5px;
  /*align-items: center;*/
  border-style: none;
  cursor: pointer;
  font-size: 16px;
  font-family: Microsoft YaHei;
  color: white;
  text-align: center;
}

.btncontent {
  width: 32px;
  height: 21px;
  font-size: 16px;
  font-family: Microsoft YaHei;
  font-weight: 400;
  color: #ffffff;
  opacity: 1;
  margin: auto;
  display: block;
}

.content {
  height: 24px;
  font-size: 18px;
  font-family: Microsoft YaHei;
  font-weight: 400;
  color: #000000;
  opacity: 0.7;
  margin-top: 18px;
}

.footer {
  position: absolute;
  bottom: 46px;
}

.time,
.limit-time,
.score {
  /* width: 89px;
  height: 21px; */
  font-size: 16px;
  font-family: Microsoft YaHei;
  /* font-weight: 400; */
  color: #000000;
  opacity: 0.7;
  /* margin: auto; */
  vertical-align: middle;
}

.icon {
  width: 20px;
  height: 20px;
  vertical-align: middle;
  position: relative;
  bottom: 2px;
  color: #338AFB;
}

.tag {
  position: absolute;
  /* width: 100px;
  height:20px; */
  width: 200px;
  height: 80px;
  line-height: 80px;
  text-align: center;
  font-size: 40px;
  color: #338AFB;
  opacity: 0.5;
  left: 120px;
  top: 80px;
  border-style: solid;
  border-color: #338AFB;
  border-width: 3px;
  transform: rotate(10deg);
  transition: opacity 0.5s;
  user-select: none;
}

.card-container:hover .tag {
  opacity: 0;
}
</style>