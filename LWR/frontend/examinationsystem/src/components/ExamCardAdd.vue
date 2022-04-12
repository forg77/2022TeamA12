<template>
  <div
    class="card-container"
    :class="[containerClass]"
    @click="this.$emit('click')"
  >
    <table style="width:100%;height:100%">
      <td style="vertical-align:middle">
        <div class="ADD" style="height: 100px; width: 100px">
          <svg-icon iconName="add" className="icon"></svg-icon>
        </div>
      </td>
    </table>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  props: ["containerClass"],
  emits: ["click"],
};
</script>


<style scoped>
.card-container {
  width: 470px;
  height: 220px;
  background: #ffffff;
  border: 1px solid rgba(0, 0, 0, 0.10196078431372549);
  /* box-shadow: 2px -3px 6px rgba(0, 0, 0, 0.1); */
  opacity: 1;
  border-radius: 10px;
  /* padding: 40px 39px 0 20px; */
  position: relative;
  display: inline-block;

  margin: 20px 10px;
  cursor: pointer;

  transition: transform 0.5s, box-shadow 0.5s;

  vertical-align: middle;
}
.card-container:hover {
  /* transform: scale(1.03); */
  box-shadow: 0px 0px 10px rgba(51, 138, 251, 0.72);
}

.icon {
  width: 100px;
  height: 100px;
}
.ADD {
  margin: auto;
  /* margin-top: 40px; */
}
</style>