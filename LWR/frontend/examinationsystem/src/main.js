import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

router.beforeEach(to => {
    if (to.meta.title) {
        document.title = to.meta.title;
    }
});

createApp(App).use(router).mount('#app');
