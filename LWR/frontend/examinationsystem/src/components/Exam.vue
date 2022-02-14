<template>
  <table class="exam" cellspacing="0" cellpadding="0">
    <tr>
      <td>
        <table cellspacing="0" cellpadding="0">
          <tr>
            <td>
              <div
                class="card"
                style="width: 262px; height: 84px; margin-top: 0"
              >
                <table style="text-align: center; margin: 0 auto;height:100%">
                  <tr>
                    <td>
                      <div class="current" style="margin:0 13px 4px 13px"></div>
                      当前
                    </td>
                    <td>
                      <div class="unanswered" style="margin:0 13px 4px 13px"></div>
                      未答
                    </td>
                    <td>
                      <div class="answered" style="margin:0 13px 4px 13px"></div>
                      已答
                    </td>
                    <td>
                      <div class="unanswered" style="margin:0 13px 4px 13px"><div class="marked"></div></div>
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
      <td>
        <table cellspacing="0" cellpadding="0">
          <tr>
            <td>
              <div
                class="card"
                style="width: 976px; height: 84px; margin-top: 0"
              ></div>
            </td>
          </tr>
          <tr>
            <td>
              <div class="card" style="width: 976px; height: 429px"></div>
            </td>
          </tr>
          <tr>
            <td>
              <div class="card" style="width: 976px; height: 70px"></div>
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

.current {
  border-radius: 50%;
  width: 30px;
  height: 30px;
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
  left:24px;
  top:2px;
}
</style>