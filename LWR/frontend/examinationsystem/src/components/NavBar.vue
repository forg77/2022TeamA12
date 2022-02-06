<template>
  <div class="nav">
    <table cellpadding="0" cellspacing="0" style="width: 100%; height: 100%">
      <tr>
        <td>
          <table cellpadding="0" cellspacing="0" style="height: 100%">
            <tr>
              <td>
                <div class="logo">
                  <span>LOGO-管理员</span>
                </div>
              </td>
              <template v-for="nav in items" :key="nav.title">
                <td>
                  <div class="child">
                    <div class="dropdown">
                      <a :class="{ active: isNavActive(nav) }">{{
                        nav.title
                      }}</a>
                      <div class="dropdown-content">
                        <template
                          v-for="child in nav.content"
                          :key="child.title"
                        >
                          <router-link
                            :to="child.link"
                            :class="{ active: $route.path == child.link }"
                            >{{ child.title }}</router-link
                          >
                        </template>
                      </div>
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
                <td><svg-icon iconName="headimg" className="headimg-icon"></svg-icon></td>
                <td><span>马老师</span></td>
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
      for (let item of items.content) {
        if (routePath == item.link) return true;
      }
      return false;
    },
  },
};
</script>


<style scoped>
.logo {
  float: left;
  background-color: #e6e6e6;
  text-align: center;
  /* position: relative; */
  /* height: 20px; */
  /* top:10px; */
  margin: 20px;
  padding: 10px 0;
  width: 120px;
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
  box-shadow: 0px 3px 6px rgba(254, 66, 66, 0.72);
  background-color: #ffffff;
  height: 80px;
  left: 0;
  right: 0;
  top: 0;
  z-index: 10;
}

.nav .child {
  float: left;
  width: 120px;
  height: 100%;
}

.nav a:hover {
  background-color: #ff3c3c;
  color: white;
}

.nav a.active {
  background-color: #ff3c3c;
  color: white;
}

.nav a {
  display: block;
  color: black;
  text-align: center;
  height: 80px;
  line-height: 80px;
  /* padding: 30px 16px; */
  margin: 0;
  text-decoration: none;

  font-family: Microsoft YaHei;
  font-size: 20px;
  /* height: calc(100% - 60px); */

  transition: background-color 0.5s, color 0.5s;
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
  border-bottom-color: #ff3c3c;

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
  background-color: #ff3c3c;
  color: white;
} */
.dropdown:hover .dropdown-content {
  display: block;
  transform: scaleY(1);
}
</style>