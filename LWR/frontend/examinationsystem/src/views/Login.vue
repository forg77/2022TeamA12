<template>
  <button class="btn" @click="login('admin1', '123')">点击登录</button>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      ajaxCancel: null,
      jumpPath: null,
    };
  },
  methods: {
    login(username, password) {
      if (this.ajaxCancel != null) {
        this.ajaxCancel();
        this.ajaxCancel = null;
      }
      axios({
        url: this.config.loginUrl,
        cancelToken: new axios.CancelToken((c) => {
          this.ajaxCancel = c;
        }),
        data: {
          username: username,
          password: password,
        },
      }).then((response) => {
        if (response.data.errCode == 0) {
          this.config.user = response.data.data;

          if (this.jumpPath) {
            this.$router.push(this.jumpPath);
          } else {
            let permissions = this.config.user.permission.split(",");
            if (permissions.indexOf("admin") >= 0) {
              this.$router.push("/admin");
            } else if (permissions.indexOf("teacher")) {
              this.$router.push("/teacher");
            } else {
              this.$router.push("/student");
            }
          }
        }
      });
    },
  },
  created() {
    this.jumpPath = this.$route.query.path;
    // console.log(this.jumpPath);
  },
  computed: {
    config() {
      return this.$store.state.config;
    },
  },
};
</script>
