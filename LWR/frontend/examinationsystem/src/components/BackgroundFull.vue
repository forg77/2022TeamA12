<template>
  <transition name="fade">
    <div class="back" v-show="show" @click="onBackClick()">
      <div style="width: 100%; height: 100%">
        <table style="width: 100%; height: 100%; vertical-align: middle">
          <td style="text-align: center;display: flex;justify-content: center;align-items: center;height: 100%">
            <div
                @click="$event.stopPropagation()"
                style=""
                :style="{width: width,height: height}"
            >
              <slot></slot>
            </div>
          </td>
        </table>
      </div>
    </div>
  </transition>
</template>

<script lang="ts">
import {defineComponent} from 'vue';
import Loading from './Loading.vue'

export default defineComponent({
  name: 'BackgroundFull',
  components: {Loading},
  emits: ['update:show'],
  methods: {
    onBackClick() {
      if (this.canClose) {
        this.$emit('update:show', false);
      }
    }
  },
  props: {
    show: {
      type: Boolean,
      default: false
    },
    canClose: {
      type: Boolean,
      default: true
    },
    width: {
      type: Number,
      default: 1000
    },
    height: {
      type: Number,
      default: 600
    }
  }
});
</script>

<style scoped>
.back {
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  background-color: #00000033;
  z-index: 30;
}
</style>
