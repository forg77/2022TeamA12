<template>
  <!-- <NavBar :items="navs" />
  <div class="title">
    {{ $route.name }}
  </div> -->
  <!-- <router-view v-slot="{ Component }">
    <transition name="fade" mode="out-in">
      <component :is="Component" />
    </transition>
  </router-view> -->

  <el-config-provider size="large" :locale="locale">
    <LoadingFull :show="$store.state.config.showLoading"></LoadingFull>
    <router-view></router-view>
  </el-config-provider>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import LoadingFull from "@/components/LoadingFull.vue";
import {ElConfigProvider} from 'element-plus'
import {zhCn} from "element-plus/es/locale";

export default defineComponent({
  components: {LoadingFull, ElConfigProvider},
  data() {
    return {
      locale: zhCn
    };
  }
});
</script>

<style>
@import "./styles/global.css";

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

.title {
  width: 1000px;
  margin-left: auto;
  margin-right: auto;
  margin-top: 90px;
  margin-bottom: 20px;
}

body {
  margin: 0;
  background-color: #eeeeee;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /* text-align: center; */
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
