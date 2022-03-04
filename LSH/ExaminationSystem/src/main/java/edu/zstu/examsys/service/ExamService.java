package edu.zstu.examsys.service;

import edu.zstu.examsys.mapper.ExamMapper;
import edu.zstu.examsys.mapper.QuestionMapper;
import edu.zstu.examsys.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ExamService {
    private ExamMapper examMapper;

    @Autowired
    public void setExamMapper(ExamMapper examMapper) {
        this.examMapper = examMapper;
    }

    public List<Exam> getExams(Integer id, String screen, String search, Condition condition) {
        return examMapper.getExams(id, screen, new Date(), search, condition);
    }

    public Integer getExamsCount(Integer id, String screen, String search) {
        return examMapper.getExamsCount(id, screen, new Date(), search);
    }

    public List<ExamPaper> getExamPapers(Integer examinee, Integer examId, Condition condition) {
        return examMapper.getExamPapers(examinee, examId, condition);
    }

    public Integer getExamPapersCount(Integer examinee, Integer examId) {
        return examMapper.getExamPapersCount(examinee, examId);
    }

    @Transactional
    public void addOrUpdateNormalAnswer(NormalAnswer answer) {
        ExamAnswer ans = examMapper.getExamAnswer(answer);
        if (ans == null)
            addNormalAnswer(answer);
        else {
            answer.setId(ans.getId());
            updateNormalAnswer(answer);
        }
    }

    @Transactional
    public void addNormalAnswer(NormalAnswer answer) {
        Integer suc = examMapper.addExamAnswer(answer);
        if (suc == 0)
            throw new RuntimeException("插入回答失败");
        suc = examMapper.addNormalAnswer(answer);
        if (suc == 0)
            throw new RuntimeException("插入回答失败");
    }

    @Transactional
    public void updateNormalAnswer(NormalAnswer answer) {
        examMapper.updateExamAnswer(answer);
        examMapper.updateNormalAnswer(answer);
    }

    public List<NormalAnswer> getNormalAnswers(Integer examinee, Integer examId) {
        return examMapper.getNormalAnswers(examinee, examId);
    }

    public List<QuestionScore> getQuestionScores(Integer examId) {
        return examMapper.getQuestionScores(examId);
    }

    public Integer updateGrade(Integer examId, Integer examinee, Float grade) {
        return examMapper.updateGrade(examId, examinee, grade);
    }

    public Integer addExamPaper(ExamPaper examPaper) {
        return examMapper.addExamPaper(examPaper);
    }

    public Integer updateExamPaper(ExamPaper examPaper) {
        return examMapper.updateExamPaper(examPaper);
    }

    public Integer deleteExamAnswers(Integer examId, Integer examinee) {
        return examMapper.deleteExamAnswers(examId, examinee);
    }

    @Transactional
    public void addOrUpdateExamPaper(ExamPaper examPaper) {
        int count = examMapper.getExamPapersCount(examPaper.getExaminee(), examPaper.getExamId());
        if (count == 0) {
            addExamPaper(examPaper);
        } else {
            updateExamPaper(examPaper);
        }
    }

    public List<GradeInfo> getGradesInfo(Integer examinee, String search, Condition con){
        return examMapper.getGradesInfo(examinee, search, con);
    }

    public Integer getGradesInfoCount(Integer examinee, String search){
        return examMapper.getGradesInfoCount(examinee);
    }

    public Integer addQuestionScore(QuestionScore score){
        return examMapper.addQuestionScore(score);
    }

    public Integer updateQuestionScore(QuestionScore score){
        return examMapper.updateQuestionScore(score);
    }

    public QuestionScore getQuestionScore(Integer examId,Integer questionId){
        return examMapper.getQuestionScore(examId, questionId);
    }

    public Integer addExam(Exam exam){
        return examMapper.addExam(exam);
    }

    public Integer deleteExam(Integer id){
        return examMapper.deleteExam(id);
    }
}
