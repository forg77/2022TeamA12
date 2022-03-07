package edu.zstu.examsys.pojo;

public class ChoiceQuestion extends Question {
    private Integer choiceId;
    private String choice;
    private String answer;

    public Integer getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Integer choiceId) {
        this.choiceId = choiceId;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return super.toString() + "\nChoiceQuestion{" +
                "choiceId=" + choiceId +
                ", choice='" + choice + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
