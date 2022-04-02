package edu.zstu.examsys.service;

import edu.zstu.examsys.mapper.ExamCorrectMapper;
import edu.zstu.examsys.mapper.ExamMapper;
import edu.zstu.examsys.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ExamCorrectService {
    private ExamCorrectMapper examCorrectMapper;

    @Autowired
    public void setExamCorrectMapper(ExamCorrectMapper examCorrectMapper) {
        this.examCorrectMapper = examCorrectMapper;
    }

    public List<CorrectInfo> getCorrectInfo(CorrectInfo info) {
        return examCorrectMapper.getCorrectInfo(info, new Date());
    }

    public Integer getCorrectInfoCount(CorrectInfo info) {
        return examCorrectMapper.getCorrectInfoCount(info, new Date());
    }

    public Integer addCorrectInfo(List<CorrectInfo> info) {
        return examCorrectMapper.addCorrectInfo(info);
    }

    public Integer updateAnswerScore(Integer examId, Integer examinee, Integer questionId, Float score, Integer corrector) {
        return examCorrectMapper.updateAnswerScore(examId, examinee, questionId, score, corrector);
    }

    public Integer getCorrectedNum(Integer examId, Integer questionId) {
        return examCorrectMapper.getCorrectedNum(examId, questionId);
    }
}
