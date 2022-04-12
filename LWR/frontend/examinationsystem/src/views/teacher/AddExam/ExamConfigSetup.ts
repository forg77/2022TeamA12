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

//考试类型
export enum ExamType {
    //固定题
    Fixed,
    //随机抽题
    Random
}

export interface ExamForm {
    formationType: FormationType;
    title: string;
    subtitle: string;
    platform: Platform[];
    year: string;
    difficulty: string;
    time: string[];
    totalScore: number;
    antiCheat: boolean;
    durationMinute: number;
    durationSecond: number;
    examType: ExamType;
    repeatTime: number;
    calGradeAtOnce: boolean;
}
