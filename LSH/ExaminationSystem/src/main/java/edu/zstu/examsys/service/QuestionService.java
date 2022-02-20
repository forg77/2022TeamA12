package edu.zstu.examsys.service;

import edu.zstu.examsys.mapper.QuestionMapper;
import edu.zstu.examsys.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    private QuestionMapper questionMapper;

    @Autowired
    public void setQuestionMapper(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    public List<QuestionBank> getBanks(Integer author, Condition con) {
        return questionMapper.getBanks(author, con);
    }

    public Integer getBanksCount(Integer author){
        return questionMapper.getBanksCount(author);
    }

    public List<Question> getQuestions(Integer bankId, Condition con){
        return questionMapper.getQuestions(bankId, con);
    }

    public Integer getQuestionsCount(Integer bankId){
        return questionMapper.getQuestionsCount(bankId);
    }

    public List<ChoiceQuestion> getChoiceQuestions(Integer questionId, Integer bankId, String type, Condition con){
        return questionMapper.getChoiceQuestions(questionId, bankId, type, con);
    }

    public List<NormalQuestion> getNormalQuestions(Integer questionId, Integer bankId, String type, Condition con){
        return questionMapper.getNormalQuestions(questionId, bankId, type, con);
    }
}
