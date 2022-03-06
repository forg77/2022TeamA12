<template>
  <div class="nav">
    <table cellpadding="0" cellspacing="0" style="width: 100%; height: 100%">
      <tr>
        <td>
          <table cellpadding="0" cellspacing="0" style="height: 100%">
            <tr>
              <td>
                <div class="logo">
                  <!--                  <span>LOGO-管理员</span>-->
                  <img alt="logo" src="@/assets/image/logo-3.png" width="158" height="70"/>
                </div>
              </td>
              <template v-for="nav in items" :key="nav.title">
                <td>
                  <div class="child">
                    <div class="dropdown">
                      <template v-if="!nav.link">
                        <a :class="{ active: isNavActive(nav) }">{{
                            nav.title
                          }}</a>
                      </template>
                      <template v-else>
                        <router-link
                            :to="nav.link"
                            :class="{ active: isNavActive(nav) }"
                        >{{ nav.title }}
                        </router-link
                        >
                      </template>
                      <template v-if="nav.content">
                        <div class="dropdown-content">
                          <template
                              v-for="child in nav.content"
                              :key="child.title"
                          >
                            <router-link
                                :to="child.link"
                                :class="{ active: $route.path == child.link }"
                            >{{ child.title }}
                            </router-link
                            >
                          </template>
                        </div>
                      </template>
                    </div>
                  </div>
                </td>
              </template>
            </tr>
          </table>
        </td>
        <td style="text-align: right">
          <div class="headimg">
            <table>
              <tr>
                <td>
                  <svg-icon
                      iconName="headimg"
                      className="headimg-icon"
                  ></svg-icon>
                </td>
                <td>
                  <span>{{ user && user.nickname }}</span>
                </td>
              </tr>
            </table>
          </div>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
export default {
  props: ["items"],
  methods: {
    isNavActive(items) {
      let routePath = this.$route.path;
      //console.log(items);
      if (items.content && items.content.length > 0) {
        for (let item of items.content) {
          if (routePath == item.link) return true;
        }
      } else {
        if (routePath == items.link) return true;
      }
      return false;
    },
  },
  computed: {
    user() {
      return this.$store.state.config.user;
    },
  },
};
</script>


<style lang="scss" scoped>
@import "src/styles/_variables.sass";

.logo {
  float: left;
  /*background-color: #e6e6e6;*/
  text-align: center;
  /* position: relative; */
  /* height: 20px; */
  /* top:10px; */
  /*margin: 15px 20px;*/
  /*padding: 10px 0;*/
  width: 120px;
  margin-left: 70px;
  margin-right: 80px;
}

.headimg {
  /* float: right; */
  margin-right: 30px;
  display: inline-block;
}

.headimg-icon {
  display: inline-block;
  margin-top: 5px;
}

.nav {
  margin: 0;
  padding: 0;
  overflow-x: auto;
  overflow-y: hidden;
  position: fixed;
  box-shadow: 0px 3px 6px rgba(51, 138, 251, 0.72);
  background-color: #ffffff;
  height: $nav-height;
  left: 0;
  right: 0;
  top: 0;
  z-index: 10;

  .child {
    float: left;
    width: 120px;
    height: 100%;
  }

  a {
    display: block;
    color: black;
    text-align: center;
    height: $nav-height;
    line-height: $nav-height;
    /* padding: 30px 16px; */
    margin: 0;
    text-decoration: none;

    font-family: Microsoft YaHei;
    font-size: 20px;
    /* height: calc(100% - 60px); */

    transition: background-color 0.5s, color 0.5s;

    &:hover {
      background-color: #338AFB;
      color: white;
    }

    .active {
      background-color: #338AFB;
      color: white;
    }
  }
}

.dropdown {
  height: 100%;
  /* line-height: 100%; */
}

.dropdown-content {
  display: block;
  position: fixed;
  background-color: white;
  width: 120px;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  padding: 0;
  margin: 0;
  border-bottom-style: solid;
  border-bottom-width: 4px;
  border-bottom-color: #338AFB;

  transform: scaleY(0);
  transform-origin: 0 0;
  transition: transform 0.5s;
}

/* .dropdown-content .item {
  height: calc(100% - 60px);
  margin: 0;
  font-family: Microsoft YaHei;
  font-size: 20px;
  padding: 30px 16px;

  transition: background-color 0.5s, color 0.5s;
}
.dropdown-content .item:hover {
  background-color: #338AFB;
  color: white;
} */
.dropdown:hover .dropdown-content {
  display: block;
  transform: scaleY(1);
}
</style>