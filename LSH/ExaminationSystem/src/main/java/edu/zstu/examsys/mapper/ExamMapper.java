package edu.zstu.examsys.mapper;

import edu.zstu.examsys.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ExamMapper {
    List<Exam> getExams(Integer id, String screen, Date now, String search, Condition con);

    Integer getExamsCount(Integer id, String screen, Date now, String search);

    List<ExamPaper> getExamPapers(Integer examinee, Integer examId, Condition con);

    Integer getExamPapersCount(Integer examinee, Integer examId);

    ExamAnswer getExamAnswer(ExamAnswer answer);

    Integer addExamAnswer(ExamAnswer answer);

    List<NormalAnswer> getNormalAnswers(Integer examinee, Integer examId);

    Integer updateExamAnswer(ExamAnswer answer);

    Integer updateNormalAnswer(NormalAnswer answer);

    Integer addNormalAnswer(NormalAnswer answer);

    List<QuestionScore> getQuestionScores(Integer examId);

    Integer updateGrade(Integer examId, Integer examinee, Float grade);

    Integer addExamPaper(ExamPaper examPaper);

    Integer updateExamPaper(ExamPaper examPaper);

    Integer deleteExamAnswers(Integer examId, Integer examinee);

    List<GradeInfo> getGradesInfo(Integer examinee, String search, Condition con);

    Integer getGradesInfoCount(Integer examinee);

    Integer addQuestionScore(QuestionScore score);

    Integer updateQuestionScore(QuestionScore score);

    QuestionScore getQuestionScore(Integer examId,Integer questionId);

    Integer addExam(Exam exam);
}
