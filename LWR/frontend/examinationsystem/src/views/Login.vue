<template>
  <button class="btn" @click="login('admin1', '123')">点击登录</button>
</template>

<script>
import axios from "axios";
import config from "../config";
export default {
  data() {
    return {
      ajaxCancel: null,
    };
  },
  methods: {
    login(username, password) {
      if (this.ajaxCancel != null) {
        this.ajaxCancel();
        this.ajaxCancel = null;
      }
      axios({
        url: config.loginUrl,
        cancelToken: new axios.CancelToken((c) => {
          this.ajaxCancel = c;
        }),
        data: {
          username: username,
          password: password,
        },
      }).then((response) => {
        if (response.data.errCode == 0) {
          config.user = response.data.data;
          this.$router.push("/admin");
        }
      });
    },
  },
};
</script>
