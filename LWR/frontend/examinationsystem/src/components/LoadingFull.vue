<template>
  <BackgroundFull v-model:show="showBack" :canClose="false">
    <Loading></Loading>
  </BackgroundFull>
</template>

<script lang="ts">
import {defineComponent} from 'vue';
import Loading from './Loading.vue'
import BackgroundFull from '@/components/BackgroundFull.vue';

export default defineComponent({
  name: 'LoadingFull',
  components: {Loading, BackgroundFull},
  data() {
    return {
      showBack: false
    };
  },
  watch: {
    show: {
      handler(newVal) {
        this.showBack = newVal;
      },
      immediate: true
    }
  },
  props: {
    show: {
      type: Boolean,
      default: false
    }
  }
});
</script>

<style scoped>
</style>
