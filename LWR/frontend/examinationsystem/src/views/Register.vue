<template>
  <!-- <button class="btn" @click="login('admin1', '123')">点击登录</button> -->
  <div class="container">
    <DialogBox v-model:show="showFailedDialog">
      <template v-slot:header>注册失败</template>
      {{ failString }}
      <template v-slot:bottom>
        <button class="btn" @click="showFailedDialog = false">确定</button>
      </template>
    </DialogBox>
    <div class="right">
      <div v-if="!success">
        <div class="title">欢迎注册</div>

        <div style="width: 490px; margin: auto">
          <table style="border-spacing: 0px 20px">
            <tr>
              <td class="tag">用户名: &nbsp;</td>
              <td>
                <input
                    type="text"
                    class="input"
                    placeholder="用户名"
                    v-model="username"
                />
              </td>
            </tr>
            <tr>
              <td class="tag">昵称: &nbsp;</td>
              <td>
                <input
                    type="text"
                    class="input"
                    placeholder="昵称"
                    v-model="nickname"
                />
              </td>
            </tr>
            <tr>
              <td class="tag">密码: &nbsp;</td>
              <td>
                <input
                    type="password"
                    class="input"
                    placeholder="密码"
                    v-model="password1"
                />
              </td>
            </tr>
            <tr>
              <td class="tag">确认密码: &nbsp;</td>
              <td>
                <input
                    type="password"
                    class="input"
                    placeholder="确认密码"
                    v-model="password2"
                />
              </td>
            </tr>
            <tr>
              <td class="tag">角色: &nbsp;</td>
              <td>
                <DropDown :values="[{value:'student',text:'学生'},{value:'teacher',text:'教师'}]"
                          @change="onDropDownChange"></DropDown>
              </td>
            </tr>
          </table>
        </div>
        <br/>
        <div>
          <button class="login-btn" @click="register()">
            注册
          </button>
        </div>
      </div>
      <div v-if="success">
        <div class="title" style="color: rgb(73, 236, 73);">注册成功</div>

        <div style="width: 490px; margin: auto">
          <table style="border-spacing: 0px 20px">
            <tr>
              <td style="text-align: right">用户名: &nbsp;</td>
              <td>
                <input
                    type="text"
                    class="input"
                    v-model="username"
                    disabled
                />
              </td>
            </tr>
            <tr>
              <td style="text-align: right">昵称: &nbsp;</td>
              <td>
                <input
                    type="text"
                    class="input"
                    v-model="nickname"
                    disabled
                />
              </td>
            </tr>
          </table>
        </div>
        <br/>
      </div>
    </div>
    <div class="left">
      <button class="register-btn" @click="$router.push('/login')">去登录</button>
    </div>
  </div>
</template>

<script lang="ts">
import axios, {Canceler} from "axios";
import {defineComponent} from "vue";
import DropDown from "@/components/DropDown.vue";
import DialogBox from "@/components/DialogBox.vue";
import {ErrCode, Response} from "@/models";

export interface RegisterData {
  username: string;
  password: string;
  nickname: string;
  permission: string;
}

export default defineComponent({
  components: {DropDown, DialogBox},
  data() {
    return {
      ajaxCancel: null as null | Canceler,

      username: '',
      password1: '',
      password2: '',
      nickname: '',
      permission: 'student',
      success: false,

      showFailedDialog: false,
      failString: '注册失败',
      // account: "321321"

      config: this.getConfig()
    };
  },
  methods: {
    getConfig() {
      return this.$store.state.config;
    },
    onDropDownChange(val: string) {
      this.permission = val;
    },
    register() {
      if (this.username == '') {
        this.failString = '用户名不能为空';
        this.showFailedDialog = true;
        return;
      }
      if (this.password1 == '') {
        this.failString = '密码不能为空';
        this.showFailedDialog = true;
        return;
      }
      if (this.password1 != this.password2) {
        this.failString = '密码不一致';
        this.showFailedDialog = true;
        return;
      }
      if (this.ajaxCancel != null) {
        this.ajaxCancel();
        this.ajaxCancel = null;
      }
      this.config.showLoading = true;
      axios({
        cancelToken: new axios.CancelToken((c) => {
          this.ajaxCancel = c;
        }),
        url: 'user/register',
        data: {
          username: this.username,
          password: this.password1,
          nickname: this.nickname,
          permission: this.permission
        } as RegisterData
      }).then((res) => {
        let response: Response = res.data;
        if (response.errCode == ErrCode.SUCCESS) {
          this.success = true;
        } else {
          this.failString = '注册失败，' + response.errMsg;
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
    }
  },
  created() {
    // console.log(this.jumpPath);
  },
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

.tag {
  white-space: nowrap;
  text-align: right;
}
</style>
