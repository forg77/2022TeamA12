<template>
  <!-- <button class="btn" @click="login('admin1', '123')">点击登录</button> -->
  <div class="container">
    <DialogBox v-model:show="showFailedDialog">
      <template v-slot:header>登录失败</template>
      {{ failString }}
      <template v-slot:bottom>
        <button class="btn" @click="showFailedDialog = false">确定</button>
      </template>
    </DialogBox>
    <div class="left">
      <button class="register-btn">去注册</button>
    </div>
    <div class="right">
      <div class="title">欢迎登录</div>

      <div class="notice">请使用您本人的账号密码登录</div>

      <div>
        <input type="text" class="input" placeholder="请输入账号" v-model="username"/>
        <input type="password" class="input" placeholder="请输入密码" v-model="password"/>
        <div class="forget">忘记密码？</div>
      </div>
      <div>
        <button class="login-btn" @click="login(username,password)">登录</button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import axios, {Canceler} from "axios";
import DialogBox from "../components/DialogBox.vue";
import {defineComponent} from "vue";
import {Response, ErrCode} from "../models";

export default defineComponent({
  components: {DialogBox},
  data() {
    return {
      ajaxCancel: null as Canceler | null,
      jumpPath: null as string | null,

      username: "admin1",
      password: "123",

      showFailedDialog: false,
      failString: '',

      config: this.getConfig()
    };
  },
  methods: {
    getConfig() {
      return this.$store.state.config;
    },
    login(username: string, password: string) {
      if (this.ajaxCancel != null) {
        this.ajaxCancel();
        this.ajaxCancel = null;
      }
      this.config.showLoading = true;
      axios({
        url: this.config.urls.login,
        cancelToken: new axios.CancelToken((c) => {
          this.ajaxCancel = c;
        }),
        data: {
          username: username,
          password: password,
        },
      }).then((res) => {
        let response: Response = res.data;
        if (response.errCode == ErrCode.SUCCESS) {
          this.config.user = response.data;

          if (this.jumpPath) {
            this.$router.push(this.jumpPath);
          } else {
            if (this.config.user) {
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
        } else {
          if (response.errCode == ErrCode.LOGIN_FAILED) {
            this.failString = response.errMsg;
          } else {
            this.failString = '登录失败，未知错误';
          }
          this.showFailedDialog = true;
        }
      }).catch((err) => {
        if (err.response || err.request) {
          this.failString = '连接失败';
          this.showFailedDialog = true;
        }
      }).finally(() => {
        this.config.showLoading = false;
      });
    },
  },
  created() {
    this.jumpPath = this.$route.query.path as string;

  },
  // computed: {
  //   config() {
  //     return this.$store.state.config;
  //   },
  // },
});
</script>
<style scoped>
.container {
  width: 1202.6px;
  height: 630px;
  background: #ffffff;
  box-shadow: 0px 5px 50px rgba(186, 186, 186, 0.5);
  opacity: 1;
  border-radius: 20px;
  margin: auto;
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  overflow: hidden;
}

.left {
  height: 100%;
  width: 40%;
  background-color: #5399f3;
  text-align: center;
  background-image: url(../assets/image/login.png);
  background-position: 0px 0px;
  background-size: 270% 100%;
}

.right {
  height: 100%;
  width: 60%;
  background-image: url(../assets/image/login.png);
  background-position: -550px 0px;
  background-size: 180% 100%;
}

.register-btn {
  width: 226px;
  height: 66px;
  border: 2px solid #ffffff;
  opacity: 1;
  border-radius: 39px;
  margin-top: 430px;
  color: #ffffff;
  background-color: #5399f3;
  outline: none;
  transition: background 0.5s, color 0.5s;
  font-size: 22px;
  cursor: pointer;
}

.register-btn:hover {
  color: #5399f3;
  background-color: #fff;
}

.login-btn {
  display: block;
  height: 45px;
  width: 409.5px;
  margin: auto;
  border: none;
  color: #ffffff;
  background-color: #5399f3;
  box-shadow: 0px 5px 15px rgba(58, 170, 168, 0.5);
  border-radius: 40px;
  font-size: 18px;
  outline: none;
  transition: background 0.5s, color 0.5s;
  cursor: pointer;
}

.login-btn:hover {
  background-color: #fff;
  color: #5399f3;
  box-shadow: none;
  border: 1px #5399f3 solid;
}

.title {
  width: 120px;
  font-size: 30px;
  font-family: PingFang SC;
  font-weight: bold;
  line-height: 49px;
  color: #338afb;
  opacity: 1;
  margin: auto;
  margin-top: 100px;
}

.notice {
  color: #9289a6;
  font-size: 18px;
  width: auto;
  text-align: center;
  margin-top: 10px;
  margin-bottom: 50px;
}

.input {
  /* border: solid; */
  outline: none;
  display: block;
  height: 56px;
  width: 409.5px;
  margin: auto;
  margin-top: 20px;
  border-radius: 8px;
  background-color: #f4f8f7;
  padding: 20px;
  color: #818181;
  font-size: 15px;

  outline-color: invert;
  outline-style: none;
  outline-width: 0px;
  border: none;
  border-style: none;
  text-shadow: none;
  outline-color: transparent;
  box-shadow: none;
}

.forget {
  color: #5399f3;
  font-size: 13px;
  margin-left: 156.031px;
  cursor: pointer;
  margin-top: 20px;
  margin-bottom: 50px;
}
</style>
