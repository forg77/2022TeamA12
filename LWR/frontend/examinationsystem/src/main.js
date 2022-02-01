import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import SvgIcon from '@/components/Svgicon'

router.beforeEach(to => {
  if (to.meta.title) {
    document.title = to.meta.title;
  }
});

createApp(App)
  .use(router)
  .component('svg-icon',SvgIcon)
  .mount('#app');
