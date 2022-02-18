package edu.zstu.examsys.service;

import edu.zstu.examsys.mapper.QuestionMapper;
import edu.zstu.examsys.pojo.Condition;
import edu.zstu.examsys.pojo.QuestionBank;
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
}
