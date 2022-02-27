<template>
  <transition name="fade">
    <div class="back" v-show="show">
      <div class="dialog">
        <table style="width: 100%; height: 100%; vertical-align: middle">
          <td>
            <div class="card">
              <table style="width: 100%; height: 100%">
                <tr style="height: 60px">
                  <td style="height: 60px">
                    <table
                      style="width: 100%; height: 100%; vertical-align: middle"
                      cellspacing="0"
                    >
                      <td class="cardHeader" style="width: 33%"></td>
                      <td class="cardHeader">
                          <slot name="header"></slot>
                      </td>
                      <td
                        class="cardHeader"
                        style="
                          width: 33%;
                          text-align: right;
                          padding-right: 20px;
                        "
                      >
                        <span
                          v-if="hasCross"
                          style="cursor: pointer"
                          @click="$emit('update:show', false)"
                        >
                          <svg-icon iconName="wrong"></svg-icon>
                        </span>
                      </td>
                    </table>
                  </td>
                </tr>
                <tr>
                  <td
                    class="cardHeader"
                    style="padding: 0 30px; text-align: left"
                  >
                    <slot></slot>
                  </td>
                </tr>
                <tr style="height: 50px">
                  <td
                    style="height: 50px; text-align: right; padding-right: 20px"
                  >
                    <slot name="bottom"></slot>
                    <!-- <button class="btn">确定</button> -->
                  </td>
                </tr>
              </table>
            </div>
          </td>
        </table>
      </div>
    </div>
  </transition>
</template>

<script>
export default {
  data() {
    return {
      
    };
  },
  emits: ['update:show'],
  props: {
    hasCross: {
      type: Boolean,
      default: true,
    },
    show: {
      type: Boolean,
      default: false,
    },
  },
};
</script>


<style scoped>
.back {
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  background-color: #00000033;
  z-index: 20;
}
.dialog {
  width: 100%;
  height: 100%;
}

.card {
  width: 500px;
  height: 300px;
  background-color: white;
  margin-left: auto;
  margin-right: auto;
  border-radius: 20px;
  box-shadow: 0px 0px 10px rgba(51, 138, 251, 0.72);
}

.cardHeader {
  border-bottom: solid;
  height: 100%;
  text-align: center;
  border-color: #00000033;
  border-bottom-width: 1px;

  font-size: 18px;
  font-family: Microsoft YaHei;
  color: #010101;
  opacity: 0.7;
}
</style>