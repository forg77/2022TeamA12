//组卷方式
export enum FormationType {
    //手动
    Manual,
    //自动
    Auto
}

//平台
export enum Platform {
    //移动端
    Mobile,
    //WEB端
    Web
}

export interface ExamConfig {
    formationType: FormationType;
    title: string;
    platform: Platform;

}
