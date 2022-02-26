package edu.zstu.examsys.pojo;

import java.util.Date;

public class GradeInfo {
    private Integer id;
    private Integer examId;
    private Integer examinee;
    private Float grade;
    private Date startTime;
    private Date finishTime;
    private Integer times;
    private String title;
    private String subTitle;
    private Float fullMark;
    private Long duration;
    private String type;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Float getFullMark() {
        return fullMark;
    }

    public void setFullMark(Float fullMark) {
        this.fullMark = fullMark;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "GradeInfo{" +
                "id=" + id +
                ", examId=" + examId +
                ", examinee=" + examinee +
                ", grade=" + grade +
                ", startTime=" + startTime +
                ", finishTime=" + finishTime +
                ", times=" + times +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", fullMark=" + fullMark +
                ", duration=" + duration +
                ", type='" + type + '\'' +
                '}';
    }
}
