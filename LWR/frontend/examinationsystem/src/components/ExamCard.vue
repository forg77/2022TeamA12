<template>
  <div class="card-container">
    <div class="head">
      <div class="exam-name">马克思主义基本原理概论</div>
      <div class="button"><span class="btncontent">考试</span></div>
    </div>

    <div class="content">马克思主义基本原理概论-2019年上期末考试</div>

    <table style="width: 428px" class="footer">
      <tbody>
        <td>
          <div class="time">
            <svg-icon iconName="calendar" className="calendar-icon"></svg-icon
            >&nbsp;2020-01-23
          </div>
        </td>
        <td>
          <div class="limit-time">
            <svg-icon iconName="time" className="time-icon"></svg-icon
            >&nbsp;限时120分钟
          </div>
        </td>
        <td>
          <div class="score">
            <svg-icon iconName="100" className="100-icon"></svg-icon
            >&nbsp;满分100
          </div>
        </td>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.head {
  display: flex;
  height: auto;
  align-items: center;
  justify-content: space-between;
}
.card-container {
  width: 470px;
  height: 250px;
  background: #ffffff;
  border: 1px solid rgba(0, 0, 0, 0.10196078431372549);
  box-shadow: 2px -3px 6px rgba(0, 0, 0, 0.1);
  opacity: 1;
  border-radius: 10px;
  padding: 40px 20px 0 20px;
  position: relative;
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
  margin-top: 20px;
}
.footer {
  position: absolute;
  bottom: 20px;
}
.time,
.limit-time,
.score {
  width: 89px;
  height: 21px;
  font-size: 12px;
  font-family: Microsoft YaHei;
  font-weight: 400;
  color: #000000;
  opacity: 0.7;
  margin: auto;
}
</style>