package edu.zstu.examsys.pojo;

public class ErrorCode {
    private ErrorCode() {
    }

    public static final int SUCCESS = 0;
    public static final int NO_LOGIN = 101;
    public static final int LOGIN_FAILED = 102;

    public static final int INSERT_FAILED = 201;

    public static final int EXAM_OVER = 301;
    public static final int EXAM_GOING = 301;
}
