<template>
  <div class="nav">
    <div class="logo">
      <span>LOGO-管理员</span>
    </div>

    <template v-for="nav in items" :key="nav.title">
      <div class="child">
        <div class="dropdown">
          <a :class="{ active: isNavActive(nav) }">{{ nav.title }}</a>
          <div class="dropdown-content">
            <template v-for="child in nav.content" :key="child.title">
              <router-link
                :to="child.link"
                :class="{ active: $route.path == child.link }"
                >{{ child.title }}</router-link
              >
            </template>
          </div>
        </div>
      </div>
    </template>

    <div class="headimg">
      <span>马老师</span>
    </div>
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
  float: right;
  margin: 30px;
}

.nav {
  margin: 0;
  padding: 0;
  overflow: hidden;
  box-shadow: 0px 3px 6px rgba(254, 66, 66, 0.72);
  background-color: #ffffff;
  height: 80px;
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
  padding: 30px 16px;
  margin: 0;
  text-decoration: none;

  font-family: Microsoft YaHei;
  font-size: 20px;
  height: calc(100% - 60px);

  transition: background-color 0.5s, color 0.5s;
}

.dropdown {
  /* position: relative;
  display: inline-block; */
}
.dropdown-content {
  display: block;
  position: absolute;
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