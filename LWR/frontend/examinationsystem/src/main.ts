import { createApp } from 'vue'
import { createStore } from 'vuex'
import App from './App.vue'
import router from './router/index'
import SvgIcon from '@/components/Svgicon/index.vue'
import config from '@/config.ts'

const app = createApp(App);
app.use(router)
    .component('svg-icon', SvgIcon)
    .mount('#app');

app.config.globalProperties.config = config;

const store = createStore({
    state() {
        return {
            config: config
        }
    },
    mutations: {
        setConfig(state:any, config) {
            state.config = config;
        },
        setUser(state, user) {
            state.config.user = user;
        }
    }
})

app.use(store);
