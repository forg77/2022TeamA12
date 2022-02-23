<template>
  <div class="card-container">
    <div class="head">
      <div class="exam-name"><slot name="title"></slot></div>
      <div class="button"><span class="btncontent">考试</span></div>
    </div>

    <div class="content"><slot name="subtitle"></slot></div>

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
  box-shadow: 2px -3px 6px rgba(0, 0, 0, 0.1);
  opacity: 1;
  border-radius: 10px;
  padding: 40px 39px 0 20px;
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

.icon{
  width:20px;
  height:20px;
  vertical-align: middle;
  position: relative;
  bottom: 2px;
}
</style>