<template>
  <clickDropDown :expand="expandMenu" :position="menuPos" :items="editMenu"
                 @itemClick="onMenuItemClick"></clickDropDown>
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
                        <a :class="{ active: navIndex.get(nav) }">{{
                            nav.title
                          }}</a>
                      </template>
                      <template v-else>
                        <router-link
                            :to="nav.link"
                            :class="{ active: navIndex.get(nav) }"
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
              <transition name="fade">
                <template v-if="navIndex.size===0">
                  <div class="child">
                    <a class="active">{{
                        $route.meta.pageTitle
                      }}</a>
                  </div>
                </template>
              </transition>
            </tr>
          </table>
        </td>
        <td style="text-align: right">
          <div ref="headimg" class="headimg" @click="onHeadimgClick($event)">
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

<script lang="ts">
import {defineComponent, PropType} from "vue";
import {NavItem, Pair, Pos} from "@/models";
import login from "@/views/Login.vue";
import ClickDropDown from "@/components/ClickDropDown.vue";
import axios from "axios";

export default defineComponent({
  components: {ClickDropDown},
  props: {
    items: {
      type: Array as PropType<Array<NavItem>>,
      default: () => {
        return [];
      }
    }
  },
  data() {
    return {
      navIndex: new Map() as Map<NavItem, boolean>,
      expandMenu: false,
      menuPos: {
        x: 0,
        y: 0
      } as Pos,
      editMenu: [{key: 'personal', value: '个人用户'}, {key: 'logout', value: '退出登录'}] as Pair[],
    };
  },
  watch: {
    path: {
      handler() {
        this.getNavIndex();
      },
      immediate: true
    },
    items: {
      handler() {
        this.getNavIndex();
      },
      immediate: true
    }
  },
  mounted() {
    document.addEventListener("click", () => {
      this.expandMenu = false;
    });
  },
  methods: {
    // isNavActive(items) {
    //   let routePath = this.$route.path;
    //   //console.log(items);
    //   if (items.content && items.content.length > 0) {
    //     for (let item of items.content) {
    //       if (routePath == item.link) return true;
    //     }
    //   } else {
    //     if (routePath == items.link) return true;
    //   }
    //   return false;
    // },
    onMenuItemClick(key: string) {
      if (key == 'personal') {
        this.$router.push('/config/personal');
      } else if (key == 'logout') {
        this.logout();
      }
    },
    logout() {
      axios({
        url: 'user/logout'
      }).then((res) => {
        if (res.data.errCode == 0)
          this.$router.push('/login');
      });
    },
    onHeadimgClick(event: MouseEvent) {
      event.stopPropagation();
      this.$refs.headimg;
      this.menuPos.x = event.pageX;
      this.menuPos.y = event.pageY;
      this.expandMenu = true;
    },
    getNavIndex() {
      this.navIndex.clear();
      for (let nav of this.items) {
        if (nav.link === this.path) {
          this.navIndex.set(nav, true);
        } else {
          if (nav.content) {
            for (let item of nav.content) {
              if (this.path === item.link) {
                this.navIndex.set(nav, true);
              }
            }
          }
        }
      }
    }
  },
  computed: {
    user() {
      return this.$store.state.config.user;
    },
    path() {
      return this.$route.path;
    }
  },
});
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
  margin-right: 70px;
  display: inline-block;
  cursor: pointer;
  transition: color 0.5s;

  &:hover {
    color: $primary-color;
  }
}

.headimg-icon {
  display: inline-block;
  margin-top: 5px;
}

.nav {
  margin: 0;
  padding: 0;
  overflow-x: hidden;
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
    font-size: 16px;
    /* height: calc(100% - 60px); */

    transition: background-color 0.5s, color 0.5s;

    &:hover, &.active {
      background-color: $primary-color;
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
  border-bottom-color: $primary-color;

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
