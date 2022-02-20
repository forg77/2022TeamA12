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
}
