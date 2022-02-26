<template>
  <div class="container">
    <WangEnduit
      v-model="Content"
      :isClear="isClear"
      @change="change"
      :value="Content"
    />
  </div>
</template>

<script>
import WangEnduit from '@/components/TextEdit.vue';
export default {
  components: {
    WangEnduit
  },
  data(){
    return{
      Content:"",
      isClear: false,
    }
  },
  methods:{
      change(val) {
      console.log('val',val)
    },
  }
}
</script>

<style>
.container {
  height: 200px;
  width: 100%;
}
</style>
