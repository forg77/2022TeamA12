package edu.zstu.examsys.pojo;

public class NormalQuestion extends Question{
    private Integer normalId;
    private String answer;
    private Integer answersCount;

    public Integer getNormalId() {
        return normalId;
    }

    public void setNormalId(Integer normalId) {
        this.normalId = normalId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getAnswersCount() {
        return answersCount;
    }

    public void setAnswersCount(Integer answersCount) {
        this.answersCount = answersCount;
    }

    @Override
    public String toString() {
        return "NormalQuestion{" +
                "normalId=" + normalId +
                ", answer='" + answer + '\'' +
                ", answersCount=" + answersCount +
                '}';
    }
}
