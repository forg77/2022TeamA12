<template>
  <div class="drop-down-container">
    <select class="dropdown" @change="$emit('change',$event.target.value);" :style="{width:width+'px',height:height+'px'}">
      <option v-for="val in values" :key="val.value" :value="val.value" >{{val.text}}</option>
    </select>
  </div>
</template>

<script>
export default {
  props: {
    values: {
      type: Array,
      default() {
        return [];
      },
    },
    width:{
      type:Number,
      default:133
    },
    height:{
      type:Number,
      default:40
    }
  },
  emits:["change"]
};
</script>

<style>
.drop-down-container {
  display: inline-block;
  vertical-align: middle;
}

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
  background-size: 15%;
  color: #888888;

  font-size: 18px;
}
</style>
