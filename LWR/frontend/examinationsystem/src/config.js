import axios from "axios";
import router from './router'

//网站的标题
const siteTitle = "考试系统";
//后台服务器地址
// let serverUrl = "http://139.9.58.231:8080/";
const serverUrl = "http://localhost:8082/";
//后台登录地址
const loginUrl = "user/login";

//配置axios默认值
axios.defaults.baseURL = serverUrl;
axios.defaults.method = 'post';
axios.defaults.withCredentials = true;

//配置axios拦截器
axios.interceptors.response.use((response) => {
    //配置用户未登录的处理
    if (response.config.url != "/user/userInfo") {
        let errCode = response.data && response.data.errCode;
        if (errCode == 101) {
            let path = router.currentRoute.value.path;
            if (path != "/login")
                router.push("/login?path=" + router.currentRoute.value.path);
        }
    }
    return response;
});

//当前登录的用户信息
const user = null;

// let config=new Proxy( { siteTitle: siteTitle, serverUrl: serverUrl, user: user, loginUrl: loginUrl });

export default { siteTitle: siteTitle, serverUrl: serverUrl, user: user, loginUrl: loginUrl };