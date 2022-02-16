<template>
  <NavBar :items="navs" />
  <!-- <p>当前路径：{{ $route.path }}</p> -->
  <div class="title">
    {{ $route.name }}
  </div>
  <router-view v-slot="{ Component }">
    <transition name="fade" mode="out-in">
      <component :is="Component" />
    </transition>
  </router-view>
</template>

<script>
import NavBar from "@/components/NavBar.vue";

export default {
  components: {
    NavBar,
  },
  props: {
    navs: {
      type: Array,
      default() {
        return [];
      },
    },
  },
};
</script>


