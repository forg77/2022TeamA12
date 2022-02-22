package edu.zstu.examsys.service;

import edu.zstu.examsys.mapper.ExamMapper;
import edu.zstu.examsys.mapper.QuestionMapper;
import edu.zstu.examsys.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamService {
    private ExamMapper examMapper;

    @Autowired
    public void setExamMapper(ExamMapper examMapper) {
        this.examMapper = examMapper;
    }

    public List<Exam> getExams(Integer id, Condition condition) {
        return examMapper.getExams(id, condition);
    }

    public Integer getExamsCount(Integer id) {
        return examMapper.getExamsCount(id);
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
}
