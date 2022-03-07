package edu.zstu.examsys.pojo;

public class QuestionScore {
    private Integer id;
    private Integer examId;
    private Integer questionId;
    private Float score;

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

    @Override
    public String toString() {
        return "QuestionScore{" +
                "id=" + id +
                ", examId=" + examId +
                ", questionId=" + questionId +
                ", score=" + score +
                '}';
    }
}
