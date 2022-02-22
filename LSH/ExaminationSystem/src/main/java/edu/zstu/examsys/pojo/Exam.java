package edu.zstu.examsys.pojo;

import java.util.Date;

public class Exam {
    private Integer id;
    private String title;
    private String subtitle;
    private Date earliestStartTime;
    private Date latestStartTime;
    private Long duration;
    private Integer bankId;
    private String type;
    private String selectCountJson;
    private String orderJson;
    private Integer repeatTime;
    private Boolean calGradeAtOnce;
    private Float fullMark;
    private Integer questionsCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Date getEarliestStartTime() {
        return earliestStartTime;
    }

    public void setEarliestStartTime(Date earliestStartTime) {
        this.earliestStartTime = earliestStartTime;
    }

    public Date getLatestStartTime() {
        return latestStartTime;
    }

    public void setLatestStartTime(Date latestStartTime) {
        this.latestStartTime = latestStartTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSelectCountJson() {
        return selectCountJson;
    }

    public void setSelectCountJson(String selectCountJson) {
        this.selectCountJson = selectCountJson;
    }

    public String getOrderJson() {
        return orderJson;
    }

    public void setOrderJson(String orderJson) {
        this.orderJson = orderJson;
    }

    public Integer getRepeatTime() {
        return repeatTime;
    }

    public void setRepeatTime(Integer repeatTime) {
        this.repeatTime = repeatTime;
    }

    public Boolean getCalGradeAtOnce() {
        return calGradeAtOnce;
    }

    public void setCalGradeAtOnce(Boolean calGradeAtOnce) {
        this.calGradeAtOnce = calGradeAtOnce;
    }

    public Float getFullMark() {
        return fullMark;
    }

    public void setFullMark(Float fullMark) {
        this.fullMark = fullMark;
    }

    public Integer getQuestionsCount() {
        return questionsCount;
    }

    public void setQuestionsCount(Integer questionsCount) {
        this.questionsCount = questionsCount;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", earliestStartTime=" + earliestStartTime +
                ", latestStartTime=" + latestStartTime +
                ", duration=" + duration +
                ", bankId=" + bankId +
                ", type='" + type + '\'' +
                ", selectCountJson='" + selectCountJson + '\'' +
                ", orderJson='" + orderJson + '\'' +
                ", repeatTime=" + repeatTime +
                ", calGradeAtOnce=" + calGradeAtOnce +
                ", fullMark=" + fullMark +
                ", questionsCount=" + questionsCount +
                '}';
    }
}
