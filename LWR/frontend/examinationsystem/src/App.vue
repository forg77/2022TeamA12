<template>
  <NavBar :items="navs" />
  <p>当前路径：{{ $route.path }}</p>
  <router-view v-slot="{ Component }">
    <transition name="fade" mode="out-in">
        <component :is="Component" />
    </transition>
  </router-view>
</template>

<script>
import NavBar from "@/components/NavBar.vue";

export default {
  data() {
    return {
      navs: [
        {
          title: "考试管理",
          content: [
            { title: "考试管理", link: "/examManage" },
            { title: "添加考试", link: "/addExam" },
            { title: "监考管理", link: "/invigilationManage" },
          ],
        },
        {
          title: "题库管理",
          content: [
            { title: "题库查询", link: "/questionsManage" },
            { title: "题库录入", link: "/questionsInput" },
          ],
        },
      ],
    };
  },
  components: {
    NavBar,
  },
};
</script>


<style>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

body {
  margin: 0;
  background-color: #eeeeee;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
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
