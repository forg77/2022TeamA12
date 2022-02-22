package edu.zstu.examsys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import edu.zstu.examsys.pojo.*;
import edu.zstu.examsys.service.ExamService;
import edu.zstu.examsys.service.QuestionService;
import edu.zstu.examsys.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/exam")
public class ExamController {
    private ExamService examService;

    private QuestionService questionService;

    @Autowired
    public void setExamService(ExamService examService) {
        this.examService = examService;
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/getExams")
    public String getQuestions(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        Condition con = JSONUtils.setCondition(body);
        Integer id = body.getInteger("id");

        Map<String, Object> data = new HashMap<>();
        data.put("count", examService.getExamsCount(id));
        data.put("data", examService.getExams(id, con));

        CommonData res = new CommonData(ErrorCode.SUCCESS, "成功", data);

        return JSON.toJSONString(res);
    }

    @PostMapping("/getExamPapers")
    public String getExamPapers(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        Condition con = JSONUtils.setCondition(body);
        Integer examinee = body.getInteger("examinee");
        Integer examId = body.getInteger("examId");

        Map<String, Object> data = new HashMap<>();
        data.put("count", examService.getExamPapersCount(examinee, examId));
        data.put("data", examService.getExamPapers(examinee, examId, con));

        CommonData res = new CommonData(ErrorCode.SUCCESS, "成功", data);

        return JSON.toJSONString(res);
    }

    @PostMapping("/getTime")
    public String getTime() {
        return JSON.toJSONString(new CommonData(ErrorCode.SUCCESS, "成功", new Date()));
    }

    @PostMapping("/addNormalAnswer")
    public String addNormalAnswer(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        NormalAnswer answer = new NormalAnswer();
        answer.setExamId(body.getInteger("examId"));
        answer.setExaminee(body.getInteger("examinee"));
        answer.setQuestionId(body.getInteger("questionId"));
        answer.setAnswer(body.getString("answer"));

        CommonData res;
        try {
            examService.addOrUpdateNormalAnswer(answer);
            res = new CommonData(ErrorCode.SUCCESS, "成功", answer.getId());
        } catch (Exception e) {
            e.printStackTrace();
            res = new CommonData(ErrorCode.INSERT_FAILED, "失败");
        }

        return JSON.toJSONString(res);
    }

    @PostMapping("/getAllAnswers")
    public String getAllAnswers(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        Integer examId = body.getInteger("examId");
        Integer examinee = body.getInteger("examinee");

        Map<String, Object> data = new HashMap<>();
        data.put("normal", examService.getNormalAnswers(examinee, examId));

        CommonData res = new CommonData(ErrorCode.SUCCESS, "成功", data);

        return JSON.toJSONString(res);
    }

    @PostMapping("/getQuestionScores")
    public String getQuestionScores(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        Integer examId = body.getInteger("examId");

        CommonData res = new CommonData(ErrorCode.SUCCESS, "成功", examService.getQuestionScores(examId));

        return JSON.toJSONString(res);
    }

    @PostMapping("/correctPaper")
    public String correctPaper(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        Integer examId = body.getInteger("examId");
        Integer examinee = body.getInteger("examinee");

        Exam exam = examService.getExams(examId, new Condition()).get(0);
        List<ChoiceQuestion> choiceQuestions = questionService.getChoiceQuestions(null, exam.getBankId(), null, new Condition());
        List<NormalQuestion> normalQuestions = questionService.getNormalQuestions(null, exam.getBankId(), null, new Condition());

        Map<Integer, JSONObject> correctNormalAnswers = new HashMap<>();
        for (ChoiceQuestion que : choiceQuestions) {
            correctNormalAnswers.put(que.getId(), JSON.parseObject(que.getAnswer()));
        }
        for (NormalQuestion que : normalQuestions) {
            correctNormalAnswers.put(que.getId(), JSON.parseObject(que.getAnswer()));
        }

        List<QuestionScore> questionScores = examService.getQuestionScores(examId);
        Map<Integer, Float> scores = new HashMap<>();
        for (QuestionScore score : questionScores) {
            scores.put(score.getId(), score.getScore());
        }

        float totalScore = 0;
        List<NormalAnswer> normalAnswers = examService.getNormalAnswers(examinee, examId);
        for (NormalAnswer answer : normalAnswers) {
            totalScore += scores.get(answer.getQuestionId()) * normalCorrect(JSON.parseObject(answer.getAnswer()), correctNormalAnswers.get(answer.getQuestionId()));
        }

        CommonData res = new CommonData(ErrorCode.SUCCESS, "成功", totalScore);

        return JSON.toJSONString(res);
    }

    private float normalCorrect(JSONObject answer, JSONObject correctAnswer) {
        Set<String> keys = correctAnswer.keySet();
        int correctNum = 0;
        for (String key : keys) {
            if (answer.containsKey(key) && answer.get(key).equals(correctAnswer.get(key))) {
                correctNum++;
            }
        }
        return (float) correctNum / keys.size();
    }

//    @PostMapping("/joinExam")
//    public String joinExam(@RequestBody String requestBody) {
//        JSONObject body = JSON.parseObject(requestBody);
//
//
//        CommonData res = new CommonData(ErrorCode.SUCCESS, "成功", data);
//
//        return JSON.toJSONString(res);
//    }
}
