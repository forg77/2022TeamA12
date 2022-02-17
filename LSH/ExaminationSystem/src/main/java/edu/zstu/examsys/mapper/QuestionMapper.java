package edu.zstu.examsys.mapper;

import edu.zstu.examsys.pojo.Condition;
import edu.zstu.examsys.pojo.QuestionBank;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface QuestionMapper {
    List<QuestionBank> getBanks(Integer author, Condition con);
}
