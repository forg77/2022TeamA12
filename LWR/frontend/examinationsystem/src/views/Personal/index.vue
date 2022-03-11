<template>
  <div class="outer">
    <Card :width="788" style="margin-right: 30px;margin-left: auto" :headerHeight="64">
      <template v-slot:headerLeft>
        <div style="display: flex">
          <div :class="{btn1:content==='BasicInfo',btn2:content!=='BasicInfo'}" @click="content='BasicInfo'">
            基本信息
            <div v-show="content==='BasicInfo'" class="line"></div>
          </div>
          &nbsp; &nbsp;
          <div :class="{btn1:content==='AccountManage',btn2:content!=='AccountManage'}" class="btn2"
               @click="content='AccountManage'">
            账号管理
            <div v-show="content==='AccountManage'" class="line"></div>
          </div>
        </div>
      </template>
      <template v-slot:content>
        <div class="inner">
          <transition name="fade" mode="out-in">
            <component :is="content"></component>
          </transition>
        </div>
      </template>
    </Card>
    <Card :width="314" style="margin-right: auto" :headerHeight="64">
      <template v-slot:headerLeft>
        认证信息
      </template>

      <template v-slot:content>
        身份
      </template>
    </Card>
  </div>
</template>

<script lang="ts">
import Card from '@/components/Card.vue';
import {defineComponent} from 'vue';
import AccountManage from '@/views/Personal/AccountManage.vue';
import BasicInfo from '@/views/Personal/BasicInfo.vue';

export default defineComponent({
  components: {Card, BasicInfo, AccountManage},
  data() {
    return {
      content: 'BasicInfo'
    };
  }
});
</script>
<style lang="scss" scoped>
.btn1 {
  font-weight: bold;
  position: relative;
  cursor: pointer;
}

.btn2 {
  //font-weight: bold;
  position: relative;
  cursor: pointer;
}

.line {
  position: absolute;
  height: 3px;
  //width: 50px;
  background-color: black;
  top: 41px;
  left: 20%;
  width: 60%;
}

.inner {
  :deep(.title1) {
    font-size: 20px;
    font-family: Microsoft YaHei;
    font-weight: 400;
    margin-bottom: 20px;
    margin-top: 20px;
  }

  :deep(.imag) {
    height: 200px;
    width: 180px;
    border: solid;
  }

  :deep(.Mcontainer) {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    margin-left: 40px;
  }

  :deep(.content) {
    padding: 40px;
    padding-top: 20px;
  }

  :deep(span) {
    color: rgb(158, 158, 158);
  }

  :deep(.change) {
    width: 179px;
    margin-top: 20px;
    text-align: center;
  }

  :deep(.write) {
    width: 300px;
    height: 30px;
    padding: 10px;
    outline: none;
    background-color: #f7f7f7b9;
    border-radius: 5px;
    box-shadow: none;
    border: solid 1px rgb(151, 151, 151);
  }
}


.outer {
  display: flex;
  //justify-content: center;
  //overflow-x: hidden;
  //margin: auto;
  //width: 100%;
}
</style>
