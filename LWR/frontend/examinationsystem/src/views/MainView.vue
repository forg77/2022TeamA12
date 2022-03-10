<template>
  <NavBar :items="navs"/>
  <!-- <p>当前路径：{{ $route.path }}</p> -->
  <div class="title">
    {{ $route.meta.pageTitle }}
  </div>
  <router-view v-slot="{ Component }">
    <transition name="fade" mode="out-in">
      <component :is="Component"/>
    </transition>
  </router-view>
</template>

<script>
import NavBar from "@/components/NavBar.vue";
import axios from "axios";
// import config from "@/config";

export default {
  data() {
    return {};
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
  created() {
    axios({
      url: "/user/userInfo",
    })
        .then((res) => {
          if (res.data.errCode != 101) {
            // this.config.user = res.data.data;
            this.$store.commit("setUser", res.data.data);
            // this.nickname = res.data.data.nickname;
            // console.log(this.$route.path);
          } else {
            // console.log(this.$route.path);
            if (this.$route.path != "/login")
              this.$router.push("/login?path=" + this.$route.path);
          }
        })
        .catch(() => {
          if (this.$route.path != "/login")
            this.$router.push("/login?path=" + this.$route.path);
        });
  },
};
</script>


