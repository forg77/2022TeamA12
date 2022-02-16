<template>
  <table
    class="exam"
    cellspacing="0"
    cellpadding="0"
    style="width: calc(100% - 150px); max-width: 1300px"
  >
    <tr>
      <td style="width: 262px">
        <table cellspacing="0" cellpadding="0">
          <tr>
            <td>
              <div class="card" style="height: 84px; margin-top: 0">
                <table
                  class="explain-text"
                  style="text-align: center; margin: 0 auto; height: 100%"
                >
                  <tr>
                    <td>
                      <div
                        class="current"
                        style="margin: 0 13px 4px 13px"
                      ></div>
                      当前
                    </td>
                    <td>
                      <div
                        class="unanswered"
                        style="margin: 0 13px 4px 13px"
                      ></div>
                      未答
                    </td>
                    <td>
                      <div
                        class="answered"
                        style="margin: 0 13px 4px 13px"
                      ></div>
                      已答
                    </td>
                    <td>
                      <div class="unanswered" style="margin: 0 13px 4px 13px">
                        <div class="marked"></div>
                      </div>
                      标记
                    </td>
                  </tr>
                </table>
              </div>
            </td>
          </tr>
          <tr>
            <td>
              <div class="card" style="width: 262px; height: 506px"></div>
            </td>
          </tr>
        </table>
      </td>
      <td style="min-width: 700px">
        <table cellspacing="0" cellpadding="0" style="width: 100%">
          <tr>
            <td>
              <div
                class="card"
                style="width: 100%; height: 84px; margin-top: 0"
              >
                <table
                  cellpadding="0"
                  cellspacing="0"
                  style="height: 100%; width: 100%; color: black; padding: 14px"
                >
                  <tr>
                    <td style="vertical-align: top">
                      <table
                        cellpadding="0"
                        cellspacing="0"
                        style="width: 100%"
                      >
                        <tr>
                          <td>
                            <table cellpadding="0" cellspacing="0">
                              <tr>
                                <td
                                  style="
                                    font-weight: bold;
                                    font-size: 21px;
                                    padding-right: 28px;
                                  "
                                >
                                  马克思主义基本原理概论
                                </td>
                                <td
                                  style="
                                    font-size: 14px;
                                    opacity: 0.7;
                                    vertical-align: bottom;
                                  "
                                >
                                  马克思主义基本原理概论-2019年上期末考试
                                </td>
                              </tr>
                            </table>
                          </td>
                          <td style="text-align: right; font-size: 21px">
                            <span style="font-weight: bold">考生：</span>大聪明
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                  <tr>
                    <td style="vertical-align: bottom">
                      <table
                        cellpadding="0"
                        cellspacing="0"
                        style="width: 100%; font-size: 14px"
                      >
                        <tr>
                          <td>
                            题量：<span
                              class="val-text"
                              style="margin-right: 28px"
                              >50</span
                            >
                            满分：<span
                              class="val-text"
                              style="margin-right: 28px"
                              >100</span
                            >
                            截止时间：<span class="val-text"
                              >2022-01-25 01:30</span
                            >
                          </td>
                          <td style="text-align: right">
                            倒计时：<span class="val-text">100</span>分钟<span
                              class="val-text"
                              >30</span
                            >秒
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </table>
              </div>
            </td>
          </tr>
          <tr>
            <td>
              <div class="card" style="width: 100%; height: 426px"></div>
            </td>
          </tr>
          <tr>
            <td>
              <div class="card" style="width: 100%; height: 70px">
                <table cellpadding="0" cellspacing="0" style="width:100%;height:100%">
                  <tr>
                    <td>上一题</td>
                    <td>标记</td>
                    <td>下一题</td>
                  </tr>
                </table>
              </div>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</template>

<style scoped>
.exam {
  margin-left: auto;
  margin-right: auto;
}

.card {
  background: #ffffff;
  border-radius: 10px;
  margin: 5px;
}

.explain-text {
  font-size: 12px;
  font-family: Microsoft YaHei;
}

.current {
  border-radius: 50%;
  width: 31px;
  height: 31px;
  border: 2px solid #ff3c3c;
}

.unanswered {
  width: 31px;
  height: 31px;
  border: 1px solid rgba(255, 60, 60, 0.30196078431372547);
  border-radius: 50%;
}

.answered {
  width: 31px;
  height: 31px;
  background: #ff3c3c;
  border: 1px solid rgba(255, 60, 60, 0.30196078431372547);
  border-radius: 50%;
}

.marked {
  width: 7px;
  height: 7px;
  background: #ff3c3c;
  border-radius: 50%;
  position: relative;
  left: 24px;
  top: 2px;
}

.val-text {
  color: #ff3c3c;
}
</style>