<template>
  <div class="content">
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
</template>

<script>
export default {
  name: "AccountManage"
}
</script>

<style scoped>

</style>
