package edu.zstu.examsys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import edu.zstu.examsys.pojo.*;
import edu.zstu.examsys.service.ExamCorrectService;
import edu.zstu.examsys.service.ExamService;
import edu.zstu.examsys.service.QuestionService;
import edu.zstu.examsys.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/examCorrect")
public class ExamCorrectController {
    private ExamService examService;
    private QuestionService questionService;
    private ExamCorrectService examCorrectService;

    @Autowired
    public void setExamCorrectService(ExamCorrectService examCorrectService) {
        this.examCorrectService = examCorrectService;
    }

    @Autowired
    public void setExamService(ExamService examService) {
        this.examService = examService;
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/getAllCorrectInfo")
    public String getAllCorrectInfo(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        Integer correctorId = body.getInteger("correctorId");
        Integer examId = body.getInteger("examId");

        Map<String, Object> data = new HashMap<>();

        List<CorrectInfo> info = examCorrectService.getCorrectInfo(new CorrectInfo(correctorId, examId));
        Map<String, Object> correctInfo = new HashMap<>();
        correctInfo.put("data", info);
        correctInfo.put("count", examCorrectService.getCorrectInfoCount(new CorrectInfo(correctorId, examId)));
        data.put("correctInfo", correctInfo);

        Exam exam = examService.getExams(examId, null, null, new Condition()).get(0);
        data.put("exam", exam);

        Map<String, Object> questions = new HashMap<>();
        List<ChoiceQuestion> choiceQuestions = questionService.getChoiceQuestions(null, exam.getBankId(), null, new Condition());
        List<NormalQuestion> normalQuestions = questionService.getNormalQuestions(null, exam.getBankId(), null, new Condition());

        questions.put("choice", choiceQuestions);
        questions.put("normal", normalQuestions);

        Map<String, Object> correctedNum = new HashMap<>();
        for (ChoiceQuestion choiceQuestion : choiceQuestions) {
            correctedNum.put(String.valueOf(choiceQuestion.getId()), examCorrectService.getCorrectedNum(examId, choiceQuestion.getId()));
        }
        for (NormalQuestion normalQuestion : normalQuestions) {
            correctedNum.put(String.valueOf(normalQuestion.getId()), examCorrectService.getCorrectedNum(examId, normalQuestion.getId()));
        }
        questions.put("correctedNum", correctedNum);

        data.put("questions", questions);

        //获取题目的分数信息
        List<QuestionScore> questionScores = examService.getQuestionScores(examId);
        data.put("questionScores", questionScores);

        CommonData res = new CommonData(ErrorCode.SUCCESS, "成功", data);

        return JSON.toJSONString(res);
    }

    //批改某一题
    @PostMapping("/setScore")
    public String getAllAnswers(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        Integer examId = body.getInteger("examId");
        Integer examinee = body.getInteger("examinee");
        Integer questionId = body.getInteger("questionId");
        Float score = body.getFloat("score");
        Integer corrector = body.getInteger("corrector");

//        System.out.println(examId + " " + examinee + " " + questionId + " " + score + " " + corrector);
        Integer lines = examCorrectService.updateAnswerScore(examId, examinee, questionId, score, corrector);
//        if (lines == 1) {
//            NormalAnswer answer = new NormalAnswer();
//            answer.setExamId(examId);
//            answer.setExaminee(examinee);
//            answer.setQuestionId(questionId);
//            answer.setScore(score);
//            answer.setCorrector(corrector);
//            answer.setAnswer(null);
//            examService.addNormalAnswer(answer);
//        }
//
//        examService.addOrUpdateNormalAnswer(answer);

        CommonData res = new CommonData(ErrorCode.SUCCESS, "成功");

        return JSON.toJSONString(res);
    }

    //提交考试，计算分数
    @PostMapping("/calculateScore")
    public String calculateScore(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        Integer examId = body.getInteger("examId");
        Integer examinee = body.getInteger("examinee");

        Float score = 0f;
        List<NormalAnswer> normalAnswers = examService.getNormalAnswers(examinee, examId);
        for (NormalAnswer answer : normalAnswers) {
            if (answer.getScore() != null)
                score += answer.getScore();
        }
        examService.updateGrade(examId, examinee, score);

        CommonData res = new CommonData(ErrorCode.SUCCESS, "成功", score);

        return JSON.toJSONString(res);
    }

    //
    @PostMapping("/getAllQuestions")
    public String getAllQuestions(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        Condition con = JSONUtils.setCondition(body);
        Integer bankId = body.getInteger("bankId");
        Integer examId = body.getInteger("examId");

        Map<String, Object> data = new HashMap<>();
        List<ChoiceQuestion> choiceQuestions = questionService.getChoiceQuestions(null, bankId, null, con);
        List<NormalQuestion> normalQuestions = questionService.getNormalQuestions(null, bankId, null, con);

        data.put("choice", choiceQuestions);
        data.put("normal", normalQuestions);

        Map<String, Object> correctedNum = new HashMap<>();
        for (ChoiceQuestion choiceQuestion : choiceQuestions) {
            correctedNum.put(String.valueOf(choiceQuestion.getId()), examCorrectService.getCorrectedNum(examId, choiceQuestion.getId()));
        }
        for (NormalQuestion normalQuestion : normalQuestions) {
            correctedNum.put(String.valueOf(normalQuestion.getId()), examCorrectService.getCorrectedNum(examId, normalQuestion.getId()));
        }
        data.put("correctedNum", correctedNum);

        CommonData res = new CommonData(ErrorCode.SUCCESS, "成功", data);

        return JSON.toJSONString(res);
    }

    //自动批改
    @PostMapping("/autoCorrectObjective")
    @Transactional
    public String autoCorrectObjective(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        Integer examId = body.getInteger("examId");
        Integer examinee = body.getInteger("examinee");

        Exam exam = examService.getExams(examId, null, null, new Condition()).get(0);
        List<ChoiceQuestion> choiceQuestions = questionService.getChoiceQuestions(null, exam.getBankId(), null, new Condition());
        List<NormalQuestion> normalQuestions = questionService.getNormalQuestions(null, exam.getBankId(), null, new Condition());

        Map<Integer, JSONObject> correctNormalAnswers = new HashMap<>();
        Set<Integer> objective = new HashSet<>();
        for (ChoiceQuestion que : choiceQuestions) {
            correctNormalAnswers.put(que.getId(), JSON.parseObject(que.getAnswer()));
            String type = que.getType();
            if ("choice".equals(type) || "multi_choice".equals(type) || "completion".equals(type)) {
                objective.add(que.getId());
            }
        }
        for (NormalQuestion que : normalQuestions) {
            correctNormalAnswers.put(que.getId(), JSON.parseObject(que.getAnswer()));
            String type = que.getType();
            if ("choice".equals(type) || "multi_choice".equals(type) || "completion".equals(type)) {
                objective.add(que.getId());
            }
        }

        List<QuestionScore> questionScores = examService.getQuestionScores(examId);
        Map<Integer, QuestionScore> scores = new HashMap<>();
        for (QuestionScore score : questionScores) {
            scores.put(score.getQuestionId(), score);
        }

        float totalScore = 0;
        List<NormalAnswer> normalAnswers = examService.getNormalAnswers(examinee, examId);
        Map<Integer, NormalAnswer> normalAnswersIndex = new HashMap<>();
        for (NormalAnswer answer : normalAnswers) {
            normalAnswersIndex.put(answer.getQuestionId(), answer);
        }

//        for (NormalAnswer answer : normalAnswers) {
//            if (!scores.get(answer.getQuestionId()).getAutoCorrect())
//                continue;
//            if (!objective.contains(answer.getQuestionId()))
//                continue;
//            if (answer.getScore() != null)
//                continue;
//            float score = scores.get(answer.getQuestionId()).getScore() * normalCorrect(JSON.parseObject(answer.getAnswer()), correctNormalAnswers.get(answer.getQuestionId()));
//            answer.setScore(score);
//            examService.updateNormalAnswer(answer);
//            totalScore += score;
//        }

        List<Integer> correctedId = new LinkedList<>();

        for (Integer id : objective) {
            if (!scores.get(id).getAutoCorrect())
                continue;
            NormalAnswer answer = normalAnswersIndex.get(id);
            if (answer != null) {
                if (answer.getScore() != null)
                    continue;
                float score = scores.get(answer.getQuestionId()).getScore() * normalCorrect(JSON.parseObject(answer.getAnswer()), correctNormalAnswers.get(answer.getQuestionId()));
                answer.setScore(score);
                examService.updateNormalAnswer(answer);
                totalScore += score;
            } else {
                examCorrectService.updateAnswerScore(examId, examinee, id, 0f, null);
            }
            correctedId.add(id);
        }
//        examService.updateGrade(examId, examinee, totalScore);

        Integer paperId = examService.getExamPapers(examinee, examId, new Condition()).get(0).getId();
        examCorrectService.setObjectiveAutoCorrected(paperId, true);
        CommonData res = new CommonData(ErrorCode.SUCCESS, "成功", correctedId);

        return JSON.toJSONString(res);
    }

    private float normalCorrect(JSONObject answer, JSONObject correctAnswer) {
        Set<String> keys = correctAnswer.keySet();
        int correctNum = 0;
        String a;
        for (String key : keys) {
            Object correctAnswerProcess =  correctAnswer.get(key);
            if (correctAnswerProcess.getClass().equals(String.class)) {
                correctAnswerProcess = ((String) correctAnswerProcess).replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "").replaceAll("[(/>)<]", "");
            }
            if (answer.containsKey(key) && correctAnswerProcess.equals(answer.get(key))) {
                correctNum++;
            }
        }
        return (float) correctNum / keys.size();
    }
}
