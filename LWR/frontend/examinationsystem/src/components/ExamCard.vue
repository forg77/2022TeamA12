<template>
  <div class="card-container">
    <div class="head">
      <div class="exam-name">马克思主义基本原理概论</div>
      <div class="button"><span class="btncontent">管理</span></div>
    </div>

    <div class="content">马克思主义基本原理概论-2019年上期末考试</div>

    <div class="foot">
      <div class="time">2020-01-23</div>
      <div class="limit-time">限时120分钟</div>
      <div class="score">满分100</div>
    </div>
  </div>
</template>

<style scoped>
.head {
  display: flex;
  border: solid;
  height: auto;
  align-items: center;
  justify-content: space-between;
}
.card-container {
  width: 470px;
  height: 220px;
  background: #ffffff;
  border: 1px solid rgba(0, 0, 0, 0.10196078431372549);
  box-shadow: 2px -3px 6px rgba(0, 0, 0, 0.1);
  opacity: 1;
  border-radius: 10px;
  padding: 40px 20px 0 20px;
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
  display: flex;
  width: 64px;
  height: 31px;
  background: #ff3c3c;
  opacity: 1;
  border-radius: 5px;
  align-items: center;
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
  border: solid;
}
.foot {
  display: flex;
  justify-content: space-around;
}
.time,.limit-time,.score {
  border: solid;
  width: 89px;
  height: 21px;
  font-size: 12px;
  font-family: Microsoft YaHei;
  font-weight: 400;
  color: #000000;
  opacity: 0.7;
}
</style>