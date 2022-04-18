package edu.zstu.examsys.mapper;

import edu.zstu.examsys.pojo.CorrectInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface ExamCorrectMapper {
    List<CorrectInfo> getCorrectInfo(CorrectInfo info, Date now);

    Integer getCorrectInfoCount(CorrectInfo info, Date now);

    Integer addCorrectInfo(List<CorrectInfo> info);

    Integer updateAnswerScore(Integer examId, Integer examinee, Integer questionId, Float score, Integer corrector);

    Integer getCorrectedNum(Integer examId, Integer questionId);

    Integer setObjectiveAutoCorrected(Integer paperId,Boolean corrected);
}
