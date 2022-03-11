<template>
  <div class="dropdown-click" :class="{expand:expand}" :style="{top:position.y+'px',left:position.x+'px'}">
    <div class="dropdown-content" v-for="item in items" :key="item.key" @click="$emit('itemClick',item.key)">
      {{ item.value }}
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent, PropType} from "vue";
import {Pair, Pos} from "@/models";

export default defineComponent({
  name: "ClickDropDown",
  props: {
    expand: {
      type: Boolean,
      default: true
    },
    position: {
      type: Object as PropType<Pos>,
      default(): Pos {
        return {
          x: 0,
          y: 0
        };
      }
    },
    items: {
      type: Array as PropType<Array<Pair>>,
      default(): Array<Pair> {
        return [];
      }
    }
  },
  emits: ["itemClick"]
});
</script>

<style lang="scss" scoped>
@import "src/styles/_variables";

.dropdown-click {
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
  z-index: 15;

  transform: scaleY(0);
  transform-origin: 0 0;
  transition: transform 0.3s;
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
