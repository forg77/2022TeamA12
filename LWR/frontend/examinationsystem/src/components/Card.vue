<template>
  <div class="cardHeader">
    <table style="width: 100%;height:100%;font-size:0">
      <tr>
        <td style="text-align: left">
          <div class="headerContent">
            <slot name="headerLeft"></slot>
          </div>
        </td>
        <td style="text-align: right">
          <div class="headerContent" style="text-align: left;">
            <slot name="headerRight"></slot>
          </div>
        </td>
      </tr>
    </table>
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
  /* vertical-align: middle; */
}

.headerContent {
  /* height: 40px;
  padding: 20px; */
  /* margin-top: 25px; */
  margin-left: 30px;
  margin-right: 30px;

  font-size: 18px;
  /* line-height: 40px; */
  font-family: Microsoft YaHei;

  display: inline-block;
  /* vertical-align: middle; */
}

/* .headerContent *{
  display: inline;
} */

.card {
  width: 1000px;
  /* height: 300px; */
  background-color: white;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 30px;
}
</style>