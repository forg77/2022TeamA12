<template>
  <div class="cardHeader">
    <div class="headerContent" style="float: left">
      <slot name="headerLeft"></slot>
    </div>

    <div class="headerContent" style="float: right">
      <slot name="headerRight"></slot>
    </div>
  </div>
  <div class="card">
    <slot name="content"></slot>
  </div>
</template>

<style scoped>
.cardHeader {
  width: 1000px;
  height: 80px;
  background-color: white;
  margin-left: auto;
  margin-right: auto;
  border-bottom-style: solid;
  border-color: #00000033;
  border-bottom-width: 1px;
}

.headerContent {
  height: 60px;
  padding: 10px;
  /* margin-top: 25px; */
  margin-left: 30px;
  margin-right: 30px;

  font-size: 18px;
  line-height: 60px;
  font-family: Microsoft YaHei;
}

.card {
  width: 1000px;
  /* height: 300px; */
  background-color: white;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 30px;
}
</style>