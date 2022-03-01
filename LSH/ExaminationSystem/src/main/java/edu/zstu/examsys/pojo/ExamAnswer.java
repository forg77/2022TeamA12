package edu.zstu.examsys.pojo;

public class ExamAnswer {
    private Integer id;
    private Integer examId;
    private Integer examinee;
    private Integer questionId;
    private Float score;
    private Integer corrector;

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

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getCorrector() {
        return corrector;
    }

    public void setCorrector(Integer corrector) {
        this.corrector = corrector;
    }

    @Override
    public String toString() {
        return "ExamAnswer{" +
                "id=" + id +
                ", examId=" + examId +
                ", examinee=" + examinee +
                ", questionId=" + questionId +
                ", score=" + score +
                ", corrector=" + corrector +
                '}';
    }
}
