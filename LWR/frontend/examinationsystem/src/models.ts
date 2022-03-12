//定义数据模型

//用户结构
export interface User {
    id: number;
    username: string;
    nickname: string;
    permission: string;
}

//后台响应结构
export interface Response {
    errCode: number;
    errMsg: string;
    data?: any;
}

//后台接口url
export interface Urls {
    server: string;
    login: string;
    //...
}

//后台错误代码
export const enum ErrCode {
    SUCCESS = 0,

    NO_LOGIN = 101,
    LOGIN_FAILED = 102,

    INSERT_FAILED = 201,
    QUERY_FAILED = 202,
    DELETE_FAILED = 203,

    EXAM_OVER = 301,
    EXAM_GOING = 302,
    EXAM_TIME_OUT = 303,
    EXAM_MISSING_LATEST_TIME = 304,
    EXAM_NOT_STARTED = 305
}

//导航栏子结构
export interface NavSubItem {
    title: string;
    link?: string;
}

//导航栏结构
export interface NavItem {
    title: string;
    link?: string;
    content?: Array<NavSubItem>;
}

//位置
export interface Pos {
    x: number;
    y: number;
}

//键值对
export interface Pair {
    key: string;
    value: string;
}
