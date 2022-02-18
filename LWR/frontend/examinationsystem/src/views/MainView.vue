<template>
  <NavBar :items="navs" :nickname="nickname" />
  <!-- <p>当前路径：{{ $route.path }}</p> -->
  <div class="title">
    {{ $route.meta.pageTitle }}
  </div>
  <router-view v-slot="{ Component }">
    <transition name="fade" mode="out-in">
      <component :is="Component" />
    </transition>
  </router-view>
</template>

<script>
import NavBar from "@/components/NavBar.vue";
import axios from "axios";
import config from "@/config";

export default {
  data() {
    return {
      nickname: "",
    };
  },
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
  created: async function () {
    await axios({
      url: "/user/userInfo",
    })
      .then((res) => {
        if (res.data.errCode != 101) {
          config.user = res.data;
          this.nickname = res.data.data.nickname;
        } else this.$router.push("/login");
      })
      .catch(() => {
        this.$router.push("/login");
      });
  },
};
</script>


