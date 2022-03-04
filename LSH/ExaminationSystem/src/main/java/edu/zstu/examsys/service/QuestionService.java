package edu.zstu.examsys.service;

import edu.zstu.examsys.mapper.QuestionMapper;
import edu.zstu.examsys.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Integer getBanksCount(Integer author) {
        return questionMapper.getBanksCount(author);
    }

    public List<Question> getQuestions(Integer bankId, Condition con) {
        return questionMapper.getQuestions(bankId, con);
    }

    public Integer getQuestionsCount(Integer bankId) {
        return questionMapper.getQuestionsCount(bankId);
    }

    public List<ChoiceQuestion> getChoiceQuestions(Integer questionId, Integer bankId, String type, Condition con) {
        return questionMapper.getChoiceQuestions(questionId, bankId, type, con);
    }

    public List<NormalQuestion> getNormalQuestions(Integer questionId, Integer bankId, String type, Condition con) {
        return questionMapper.getNormalQuestions(questionId, bankId, type, con);
    }

    public String getQuestionType(Integer id) {
        return questionMapper.getQuestionType(id);
    }

    public Integer addQuestion(Question question) {
        return questionMapper.addQuestion(question);
    }

    public Integer updateQuestion(Question question) {
        return questionMapper.updateQuestion(question);
    }

    public Integer addChoiceQuestion(ChoiceQuestion question) {
        return questionMapper.addChoiceQuestion(question);
    }

    public Integer updateChoiceQuestion(ChoiceQuestion question) {
        return questionMapper.updateChoiceQuestion(question);
    }

    public Integer addNormalQuestion(NormalQuestion question) {
        return questionMapper.addNormalQuestion(question);
    }

    public Integer updateNormalQuestion(NormalQuestion question) {
        return questionMapper.updateNormalQuestion(question);
    }

    @Transactional
    public void addOrUpdateChoiceQuestion(ChoiceQuestion question) {
        String questionType = questionMapper.getQuestionType(question.getId());
        if (questionType == null) {
            if (questionMapper.addQuestion(question) == 0) throw new RuntimeException("添加问题失败");
            if (questionMapper.addChoiceQuestion(question) == 0) throw new RuntimeException("添加选择题失败");
        } else {
            questionMapper.updateQuestion(question);
            questionMapper.updateChoiceQuestion(question);
        }
    }

    @Transactional
    public void addOrUpdateNormalQuestion(NormalQuestion question) {
        String questionType = questionMapper.getQuestionType(question.getId());
        if (questionType == null) {
            if (questionMapper.addQuestion(question) == 0) throw new RuntimeException("添加问题失败");
            if (questionMapper.addNormalQuestion(question) == 0) throw new RuntimeException("添加选择题失败");
        } else {
            questionMapper.updateQuestion(question);
            questionMapper.updateNormalQuestion(question);
        }
    }

    public Integer deleteChoiceQuestion(Integer id) {
        return questionMapper.deleteChoiceQuestion(id);
    }

    public Integer deleteNormalQuestion(Integer id) {
        return questionMapper.deleteNormalQuestion(id);
    }

    public Integer deleteQuestion(Integer id) {
        return questionMapper.deleteQuestion(id);
    }

    public Integer addBank(QuestionBank bank) {
        return questionMapper.addBank(bank);
    }

    public Integer updateBankName(Integer id, String name) {
        return questionMapper.updateBankName(id, name);
    }
}
