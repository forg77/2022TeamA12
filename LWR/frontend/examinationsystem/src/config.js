import axios from "axios";

const siteTitle = "考试系统";
// const serverUrl = "http://139.9.58.231:8080/";
const serverUrl = "http://localhost:8082/";

axios.defaults.baseURL = serverUrl;
axios.defaults.method = 'post';

export { siteTitle, serverUrl };