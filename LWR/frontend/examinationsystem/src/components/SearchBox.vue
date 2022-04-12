<template>
  <div class="inputBox" style="width: 170px; display: inline-block;vertical-align:middle">
    <table style="width: 100%; height: 100%">
      <tr>
        <td>
          <input @input="$emit('update:text', $event.target.value)" class="text" type="text" :placeholder="placeholder" />
        </td>
        <td>
          <svg-icon iconName="search" className="icon"></svg-icon>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  emits:["update:text"],
  props: {
    placeholder: {
      type: String,
      default: "搜索",
    },
    text:{
      type: String,
      default: "",
    }
  },
};
</script>


<style scoped>
.text {
  width: calc(100% - 25px);
  height: 100%;
  padding-left: 20px;

  border: none;
  outline: none;

  /* margin-left: 10px; */

  font-size: 16px;
  font-family: Microsoft YaHei;
  color: #000000;
  opacity: 0.7;
  display: inline-block;
}

.icon{
  width:22px;
  height:22px;
  margin-right: 10px;
  color:rgb(154, 154, 154)
}
</style>