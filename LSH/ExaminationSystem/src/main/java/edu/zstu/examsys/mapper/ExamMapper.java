package edu.zstu.examsys.mapper;

import edu.zstu.examsys.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ExamMapper {
    List<Exam> getExams(Integer id, Condition condition);

    Integer getExamsCount(Integer id);

    List<ExamPaper> getExamPapers(Integer examinee, Integer examId, Condition condition);

    Integer getExamPapersCount(Integer examinee, Integer examId);

    ExamAnswer getExamAnswer(ExamAnswer answer);

    Integer addExamAnswer(ExamAnswer answer);

    List<NormalAnswer> getNormalAnswers(Integer examinee, Integer examId);

    Integer updateExamAnswer(ExamAnswer answer);

    Integer updateNormalAnswer(NormalAnswer answer);

    Integer addNormalAnswer(NormalAnswer answer);

    List<QuestionScore> getQuestionScores(Integer examId);
}
