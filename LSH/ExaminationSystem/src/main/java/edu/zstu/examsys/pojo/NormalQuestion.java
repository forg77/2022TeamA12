package edu.zstu.examsys.pojo;

public class NormalQuestion extends Question{
    private Integer normalId;
    private String answer;

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

    @Override
    public String toString() {
        return super.toString() + "\nNormalQuestion{" +
                "normalId=" + normalId +
                ", answer='" + answer + '\'' +
                '}';
    }
}
