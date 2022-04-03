package edu.zstu.examsys.pojo;

//某个老师的某份试卷的批改信息
public class CorrectInfo {
    private Integer id;
    private Integer correctorId;
    private Integer paperId;
    private Integer examId;
    private Integer examinee;
    private Long time;
    private Float grade;
    private Boolean objectiveAutoCorrected;

    public CorrectInfo(Integer correctorId, Integer examId) {
        this.correctorId = correctorId;
        this.examId = examId;
    }

    public CorrectInfo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCorrectorId() {
        return correctorId;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public void setCorrectorId(Integer correctorId) {
        this.correctorId = correctorId;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Float getGrade() {
        return grade;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }

    public Integer getExaminee() {
        return examinee;
    }

    public void setExaminee(Integer examinee) {
        this.examinee = examinee;
    }

    public Boolean getObjectiveAutoCorrected() {
        return objectiveAutoCorrected;
    }

    public void setObjectiveAutoCorrected(Boolean objectiveAutoCorrected) {
        this.objectiveAutoCorrected = objectiveAutoCorrected;
    }

    @Override
    public String toString() {
        return "CorrectInfo{" +
                "id=" + id +
                ", correctorId=" + correctorId +
                ", paperId=" + paperId +
                ", examId=" + examId +
                ", examinee=" + examinee +
                ", time=" + time +
                ", grade=" + grade +
                ", objectiveAutoCorrected=" + objectiveAutoCorrected +
                '}';
    }
}
