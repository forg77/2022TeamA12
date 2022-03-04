package edu.zstu.examsys.mapper;

import edu.zstu.examsys.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface QuestionMapper {
    List<QuestionBank> getBanks(Integer author, Condition con);

    Integer getBanksCount(Integer author);

    List<Question> getQuestions(Integer bankId, Condition con);

    Integer getQuestionsCount(Integer bankId);

    List<ChoiceQuestion> getChoiceQuestions(Integer questionId, Integer bankId, String type, Condition con);

    List<NormalQuestion> getNormalQuestions(Integer questionId, Integer bankId, String type, Condition con);

    String getQuestionType(Integer id);

    Integer addQuestion(Question question);

    Integer updateQuestion(Question question);

    Integer addChoiceQuestion(ChoiceQuestion question);

    Integer updateChoiceQuestion(ChoiceQuestion question);

    Integer addNormalQuestion(NormalQuestion question);

    Integer updateNormalQuestion(NormalQuestion question);

    Integer deleteChoiceQuestion(Integer id);

    Integer deleteNormalQuestion(Integer id);

    Integer deleteQuestion(Integer id);

    Integer addBank(QuestionBank bank);

    Integer updateBankName(Integer id,String name);

}
