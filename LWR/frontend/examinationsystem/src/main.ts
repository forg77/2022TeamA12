import { createApp } from 'vue'
import { createStore } from 'vuex'
import App from './App.vue'
import router from './router/index'
import SvgIcon from '@/components/Svgicon/index.vue'
import { config } from '@/config.ts'
import 'element-plus/dist/index.css'

import * as echarts from 'echarts';
const app = createApp(App);
app.use(router)
  .component('svg-icon', SvgIcon);

// app.config.globalProperties.$echarts = echarts


const store = createStore({
  state() {
    return {
      config: config
    }
  },
  mutations: {
    setConfig(state: any, config) {
      state.config = config;
    },
    setUser(state, user) {
      state.config.user = user;
    }
  }
});

app.use(store).mount('#app');
