import axios from "axios";
import router from './router/index'
import {ErrCode, Urls, User} from "@/models";

//网站的标题
// const siteTitle = "考试系统";
//后台服务器地址
// const serverUrl = "http://139.9.58.231:8080/";
// const serverUrl = "http://localhost:8082/";
//后台登录地址
// const loginUrl = "user/login";

export interface Config {
    urls: Urls;
    user: User | null;
    siteTitle: string;
    showLoading: boolean;
}

//当前登录的用户信息
// const user: User | null = null;

const urls: Urls = {
    server: 'http://localhost:8082/',
    // server: 'http://139.9.58.231:8080/',
    login: 'user/login'
};

const config: Config = {
    user: null,
    urls,
    siteTitle: '考试系统',
    showLoading: false
};

//配置axios默认值
axios.defaults.baseURL = urls.server;
axios.defaults.method = 'post';
axios.defaults.withCredentials = true;

//配置axios拦截器
axios.interceptors.response.use((response) => {
    //配置用户未登录的处理
    if (response.config.url != '/user/userInfo') {
        const errCode = response.data && response.data.errCode;
        if (errCode == ErrCode.NO_LOGIN) {
            const path = router.currentRoute.value.path;
            if (path != '/login')
                router.push('/login?path=' + router.currentRoute.value.path);
        }
    }
    return response;
});

// let config=new Proxy( { siteTitle: siteTitle, serverUrl: serverUrl, user: user, loginUrl: loginUrl });

// export default {siteTitle, serverUrl, user, loginUrl};
export {config};
