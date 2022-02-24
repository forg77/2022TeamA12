<template>
  <div class="container">
    <select id="sex">
      <option value="">信息学院</option>
      <option value="">服装学院</option>
      <option value="" selected="selected">全部学院</option>
    </select>
  </div>
</template>

<script>
export default {};
</script>

<style>
select {
  width: 133px;
  height: 40px;
  border: 1px solid rgba(0, 0, 0, 0.30196078431372547);
  box-shadow: 0px -3px 6px rgba(0, 0, 0, 0.1);
  padding-left: 10px;
  padding-right: 10px;
  opacity: 1;
  border-radius: 10px;
  outline: none;
  cursor: pointer;
  appearance: none;
  -moz-appearance: none;
  -webkit-appearance: none;
  background: url("../assets/image/triangle.png") no-repeat 110px
    transparent;
  background-size: 10%;
  color: #888888;
}
</style>