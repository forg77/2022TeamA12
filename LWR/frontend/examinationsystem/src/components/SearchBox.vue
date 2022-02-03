<template>
  <div class="inputBox" style="width: 200px;display:inline-block">
    <table style="width:100%;height:100%;">
      <tr>
        <td>
          <input class="text" type="text" placeholder="搜索" />
        </td>
      </tr>
    </table>
    <!-- <div style="width: 100%;height:100%">
      <input class="text" type="text" placeholder="搜索" />
    </div> -->
  </div>
</template>

<style scoped>
.text {
  width: calc(100% - 60px);
  height: 100%;
  padding-left: 20px;

  border: none;
  outline: none;

  /* margin-left: 10px; */

  font-size: 18px;
  font-family: Microsoft YaHei;
  color: #000000;
  opacity: 0.7;

  display: inline-block;
}
</style>