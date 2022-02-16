import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import SvgIcon from '@/components/Svgicon'

createApp(App)
    .use(router)
    .component('svg-icon', SvgIcon)
    .mount('#app');