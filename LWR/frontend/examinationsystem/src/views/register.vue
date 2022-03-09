<template>
  <!-- <button class="btn" @click="login('admin1', '123')">点击登录</button> -->
  <div class="container">
    <div class="right">
      <div v-if="false">
        <div class="title">欢迎注册</div>

        <div style="width: 490px; margin: auto">
          <table style="border-spacing: 0px 20px">
            <tr>
              <td style="text-align: right">手机号: &nbsp;</td>
              <td>
                <input
                  type="number"
                  class="input"
                  placeholder="请输入手机号"
                  v-model="phone"
                />
              </td>
            </tr>
            <tr>
              <td style="text-align: right">密码: &nbsp;</td>
              <td>
                <input
                  type="text"
                  class="input"
                  placeholder="请输入密码"
                  v-model="password1"
                />
              </td>
            </tr>
            <tr>
              <td style="text-align: right">确认密码: &nbsp;</td>
              <td>
                <input
                  type="text"
                  class="input"
                  placeholder="请确认密码"
                  v-model="password2"
                />
              </td>
            </tr>
          </table>
        </div>
        <br />
        <div>
          <button class="login-btn" @click="login(username, password)">
            注册
          </button>
        </div>
      </div>
      <div v-if="true">
        <div class="title" style="color: rgb(73, 236, 73);">注册成功</div>

        <div style="width: 490px; margin: auto">
          <table style="border-spacing: 0px 20px">
            <tr>
              <td style="text-align: right">账号: &nbsp;</td>
              <td>
                <input
                  type="number"
                  class="input"
                  v-model="account"
                  disabled
                />
              </td>
            </tr>
            <tr>
              <td style="text-align: right">密码: &nbsp;</td>
              <td>
                <input
                  type="text"
                  class="input"
                  v-model="password1"
                  disabled
                />
              </td>
            </tr>
          </table>
        </div>
        <br />
      </div>
    </div>
    <div class="left">
      <button class="register-btn">去登录</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      ajaxCancel: null,
      jumpPath: null,

      phone: "",
      password1: "231321",
      password2: "",
      account:"321321"
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
</style>