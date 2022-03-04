<template>
  <div class="dropdown" :class="{expand:expand}" :style="{top:position.y+'px',left:position.x+'px'}">
    <div class="dropdown-content" v-for="item in items" :key="item">
      {{ item }}
    </div>
<!--    <div class="dropdown-content">-->
<!--      选项2-->
<!--    </div>-->
  </div>
</template>

<script>
export default {
  name: "ClickDropDown",
  props: {
    expand: {
      type: Boolean,
      default: true
    },
    position: {
      type: Object,
      default() {
        return {
          x: 0,
          y: 0
        };
      }
    },
    items: {
      type: Array,
      default() {
        return [];
      }
    }
  },
}
</script>

<style lang="scss" scoped>
@import "src/styles/_variables";

.dropdown {
  display: block;
  position: absolute;
  background-color: white;
  width: 80px;
  /*height:30px;*/
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  padding: 0;
  margin: 0;
  border-bottom-style: solid;
  border-bottom-width: 4px;
  border-bottom-color: $primary-color;
  z-index: 5;

  transform: scaleY(0);
  transform-origin: 0 0;
  transition: transform 0.5s;
}

.expand {
  transform: scaleY(1);
}

.dropdown-content {
  height: 30px;
  text-align: center;
  transition: all 0.5s;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;

  &:hover {
    background-color: $primary-color;
    color: white;
  }
}
</style>