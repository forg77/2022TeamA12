package edu.zstu.examsys.pojo;

public class ErrorCode {
    private ErrorCode() {
    }

    //成功
    public static final int SUCCESS = 0;
    //1--，用户错误
    //用户未登录
    public static final int NO_LOGIN = 101;
    //登录失败
    public static final int LOGIN_FAILED = 102;

    //2--，数据库错误
    //插入失败
    public static final int INSERT_FAILED = 201;
    //查询失败
    public static final int QUERY_FAILED = 202;
    //删除失败
    public static final int DELETE_FAILED = 203;

    //3--，考试错误
    //考试已结束
    public static final int EXAM_OVER = 301;
    //考试进行中
    public static final int EXAM_GOING = 302;
    //考试已过期
    public static final int EXAM_TIME_OUT = 303;
    //已错过最晚能够加入考试的时间
    public static final int EXAM_MISSING_LATEST_TIME = 304;
    //考试未开始
    public static final int EXAM_NOT_STARTED = 305;
}
