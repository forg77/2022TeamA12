<template>
  <div>
    <Card>
      <template v-slot:headerLeft>
        <div style="display: flex">
          <div class="btn1">基本信息</div>
          &nbsp; &nbsp;
          <div>账号管理</div>
        </div>
      </template>
      <template v-slot:content>
        <div class="content" v-if="false">
          <div class="title1">基本信息</div>

          <div style="display: flex">
            <div class="imag"></div>
            <div class="Mcontainer">
              <div style="font-size: 18px">
                姓名: <span style="">吴彦祖</span>
              </div>
              <div style="font-size: 18px">
                性别: <input type="radio" name="r1" value="man"/><span>
                  男</span
              ><input type="radio" name="r1" value="woman"/><span> 女</span>
              </div>
              <div style="font-size: 18px">
                个性签名: <span>阿巴阿巴阿巴</span>
              </div>
            </div>
          </div>

          <div class="change">
            <button>编辑头像</button>
          </div>
          <div class="title1">联系方式</div>
          <table style="border-spacing: 50px 10px">
            <tr>
              <td style="font-size: 18px; font-weight: bold">Email</td>
              <td>
                <input type="text" placeholder="尚未绑定邮箱" class="write"/>
              </td>
              <td>
                <span style="font-size: 12px; color: #1989fa">绑定邮箱</span>
              </td>
            </tr>
            <tr>
              <td style="font-size: 18px; font-weight: bold">手机号</td>
              <td>
                <input type="text" placeholder="尚未绑定手机号" class="write"/>
              </td>
              <td>
                <span style="font-size: 12px; color: #1989fa">绑定手机</span>
              </td>
            </tr>
          </table>
        </div>

        <div class="content">
          <table style="border-spacing: 10px; width: 100%">
            <tr>
              <td>
                <table>
                  <tr>
                    <td>修改密码</td>
                  </tr>
                  <tr>
                    <td>
                    <span style="color: rgb(192, 192, 192); font-size: 12px"
                    >设置复杂密码更能保护您的账号安全</span
                    >
                    </td>
                  </tr>
                </table>
              </td>
              <td>
                <button>修改密码</button>
              </td>
            </tr>

            <tr>
              <td>
                <table>
                  <tr>
                    <td>修改认证信息</td>
                  </tr>
                  <tr>
                    <td>
                    <span style="color: rgb(192, 192, 192); font-size: 12px"
                    >认证信息与您密切相关，请谨慎修改</span
                    >
                    </td>
                  </tr>
                </table>
              </td>
              <td>
                <button>修改认证</button>
              </td>
            </tr>

            <tr>
              <td>
                <table>
                  <tr>
                    <td>账号注销</td>
                  </tr>
                  <tr>
                    <td>
                    <span style="color: rgb(192, 192, 192); font-size: 12px"
                    >为保证您的账号安全，需要满足相应的条件，才能提交注销申请</span
                    >
                    </td>
                  </tr>
                </table>
              </td>
              <td>
                <button>账号注销</button>
              </td>
            </tr>
          </table>
        </div>
      </template>
    </Card>
  </div>
</template>title

<script>
import Card from "@/components/Card.vue";

export default {
  components: {Card},
};
</script>
<style scoped>
.btn1 {
  font-weight: bold;
}

.title1 {
  font-size: 20px;
  font-family: Microsoft YaHei;
  font-weight: 400;
  margin-bottom: 20px;
  margin-top: 20px;
}

.imag {
  height: 200px;
  width: 180px;
  border: solid;
}

.Mcontainer {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  margin-left: 40px;
}

.content {
  padding: 40px;
  padding-top: 20px;
}

span {
  color: rgb(158, 158, 158);
}

.change {
  width: 179px;
  margin-top: 20px;
  text-align: center;
}

.write {
  width: 300px;
  height: 30px;
  padding: 10px;
  outline: none;
  background-color: #f7f7f7b9;
  border-radius: 5px;
  box-shadow: none;
  border: solid 1px rgb(151, 151, 151);
}
</style>
