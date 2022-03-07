package edu.zstu.examsys.pojo;

import java.util.Date;

public class ExamPaper {
    private Integer id;
    private Integer examId;
    private Integer examinee;
    private String orderJson;
    private Float grade;
    private Date startTime;
    private Date finishTime;
    private Integer times;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public Integer getExaminee() {
        return examinee;
    }

    public void setExaminee(Integer examinee) {
        this.examinee = examinee;
    }

    public String getOrderJson() {
        return orderJson;
    }

    public void setOrderJson(String orderJson) {
        this.orderJson = orderJson;
    }

    public Float getGrade() {
        return grade;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    @Override
    public String toString() {
        return "ExamPaper{" +
                "id=" + id +
                ", examId=" + examId +
                ", examinee=" + examinee +
                ", orderJson='" + orderJson + '\'' +
                ", grade=" + grade +
                ", startTime=" + startTime +
                ", finishTime=" + finishTime +
                ", times=" + times +
                '}';
    }
}
