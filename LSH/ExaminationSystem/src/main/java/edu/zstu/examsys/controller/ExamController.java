package edu.zstu.examsys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import edu.zstu.examsys.pojo.*;
import edu.zstu.examsys.service.ExamService;
import edu.zstu.examsys.service.QuestionService;
import edu.zstu.examsys.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
        String search = body.getString("search");
        String screen = body.getString("screen");

        Map<String, Object> data = new HashMap<>();
        data.put("count", examService.getExamsCount(id, screen, search));
        data.put("data", examService.getExams(id, screen, search, con));

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

    @PostMapping("/joinExam")
    public String joinExam(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        Integer examId = body.getInteger("examId");
        Integer examinee = body.getInteger("examinee");
        Exam exam = examService.getExams(examId, null, null, new Condition()).get(0);
        if (new Date().compareTo(exam.getEarliestStartTime()) < 0) {
            return JSON.toJSONString(new CommonData(ErrorCode.EXAM_NOT_STARTED, "考试未开始"));
        } else if (new Date().compareTo(exam.getLatestStartTime()) >= 0) {
            return JSON.toJSONString(new CommonData(ErrorCode.EXAM_MISSING_LATEST_TIME, "已错过最晚开始时间"));
        } else if (isExamTimeOut(exam)) {
            return JSON.toJSONString(new CommonData(ErrorCode.EXAM_TIME_OUT, "考试已过期"));
        }
        List<ExamPaper> examPapers = examService.getExamPapers(examId, examinee, new Condition());
        ExamPaper examPaper;
        if (examPapers.size() > 0) {
            examPaper = examPapers.get(0);
            examPaper.setTimes(examPaper.getTimes() + 1);
        } else {
            examPaper = new ExamPaper();
            examPaper.setTimes(1);
        }
//        examPaper.setStartTime(new Date());

        CommonData res;
        if (examPaper.getStartTime() == null || isExamOver(exam, examPaper)) {
            System.out.println("A.");
            examPaper.setExamId(body.getInteger("examId"));
            examPaper.setExaminee(body.getInteger("examinee"));
            examPaper.setOrderJson(body.getString("orderJson"));
            examPaper.setStartTime(new Date());
            examPaper.setFinishTime(null);
            examPaper.setGrade(null);

            if (examPapers.size() > 0) {
                examPaper.setStartTime(new Date());
                examService.updateExamPaper(examPaper);
            } else {
                examService.addExamPaper(examPaper);
            }
            examService.deleteExamAnswers(examPaper.getExamId(), examPaper.getExaminee());

            res = new CommonData(ErrorCode.SUCCESS, "成功");
        } else {
            res = new CommonData(ErrorCode.EXAM_GOING, "考试进行中");
        }

        return JSON.toJSONString(res);
    }

    @PostMapping("/stopExam")
    public String stopExam(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        Integer examId = body.getInteger("examId");
        Integer examinee = body.getInteger("examinee");
        Exam exam = examService.getExams(examId, null, null, new Condition()).get(0);
        ExamPaper examPaper = examService.getExamPapers(examinee, examId, new Condition()).get(0);

        CommonData res;
        if (!isExamOver(exam, examPaper)) {
            examPaper.setFinishTime(new Date());
            examService.updateExamPaper(examPaper);
            res = new CommonData(ErrorCode.SUCCESS, "成功");
        } else {
            res = new CommonData(ErrorCode.EXAM_OVER, "考试已结束");
        }

        return JSON.toJSONString(res);
    }

    //获取一场考试所需的所有信息
    @PostMapping("/getAllExamInfo")
    public String getAllExamInfo(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        Condition con = JSONUtils.setCondition(body);
        Integer examinee = body.getInteger("examinee");
        Integer examId = body.getInteger("examId");

        CommonData res;

        try {
            //获取考试信息
            List<Exam> exams = examService.getExams(examId, null, null, new Condition());
            Exam exam;
            if (exams.size() > 0)
                exam = examService.getExams(examId, null, null, new Condition()).get(0);
            else
                throw new Exception("考试不存在");

            //获取考试所属题库的题目信息
            Map<String, Object> questions = new HashMap<>();
            if (exam.getBankId() != null) {
                questions.put("choice", questionService.getChoiceQuestions(null, exam.getBankId(), null, con));
                questions.put("normal", questionService.getNormalQuestions(null, exam.getBankId(), null, con));
            }

            //获取考生的试卷信息
            List<ExamPaper> examPapers = examService.getExamPapers(examinee, examId, new Condition());
            ExamPaper examPaper = null;
            if (examPapers.size() > 0)
                examPaper = examPapers.get(0);

            //获取考生的回答信息
            Map<String, Object> answers = new HashMap<>();
            answers.put("normal", examService.getNormalAnswers(examinee, examId));

            //获取题目的分数信息
            List<QuestionScore> questionScores = examService.getQuestionScores(examId);

            Map<String, Object> data = new HashMap<>();
            data.put("exam", exam);
            data.put("questions", questions);
            data.put("examPaper", examPaper);
            data.put("answers", answers);
            data.put("questionScores", questionScores);

            res = new CommonData(ErrorCode.SUCCESS, "成功", data);
        } catch (Exception e) {
            res = new CommonData(ErrorCode.QUERY_FAILED, e.getMessage());
        }

        return JSON.toJSONString(res);
    }

    @PostMapping("/correctPaper")
    public String correctPaper(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        Integer examId = body.getInteger("examId");
        Integer examinee = body.getInteger("examinee");

        Exam exam = examService.getExams(examId, null, null, new Condition()).get(0);
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
            scores.put(score.getQuestionId(), score.getScore());
        }

        float totalScore = 0;
        List<NormalAnswer> normalAnswers = examService.getNormalAnswers(examinee, examId);
        for (NormalAnswer answer : normalAnswers) {
            float score = scores.get(answer.getQuestionId()) * normalCorrect(JSON.parseObject(answer.getAnswer()), correctNormalAnswers.get(answer.getQuestionId()));
            answer.setScore(score);
            examService.updateNormalAnswer(answer);
            totalScore += score;
        }
        examService.updateGrade(examId, examinee, totalScore);

        CommonData res = new CommonData(ErrorCode.SUCCESS, "成功", totalScore);

        return JSON.toJSONString(res);
    }

    @PostMapping("/getGrades")
    public String getGrades(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        Condition con = JSONUtils.setCondition(body);
        Integer examinee = body.getInteger("examinee");
        String search = body.getString("search");

        Map<String, Object> data = new HashMap<>();
        data.put("count", examService.getGradesInfoCount(examinee, search));
        data.put("data", examService.getGradesInfo(examinee, search, con));

        CommonData res = new CommonData(ErrorCode.SUCCESS, "成功", data);

        return JSON.toJSONString(res);
    }

    @PostMapping("/addQuestionScore")
    public String addQuestionScore(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        Integer questionId = body.getInteger("questionId");
        Integer examId = body.getInteger("examId");
        Float score = body.getFloat("score");

        QuestionScore questionScore = examService.getQuestionScore(examId, questionId);
        if (questionScore == null) {
            questionScore = new QuestionScore();
            questionScore.setQuestionId(questionId);
            questionScore.setExamId(examId);
            questionScore.setScore(score);
            examService.addQuestionScore(questionScore);
        } else {
            questionScore.setScore(score);
            examService.updateQuestionScore(questionScore);
        }

        CommonData res = new CommonData(ErrorCode.SUCCESS, "成功", questionScore.getId());


        return JSON.toJSONString(res);
    }

    @PostMapping("/addExam")
    public String addExam(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        Exam exam = body.toJavaObject(Exam.class);
        Integer suc = examService.addExam(exam);
        questionService.updateBankName(exam.getBankId(), "考试题库 - " + exam.getTitle());

        CommonData res;
        if (suc > 0) {
            res = new CommonData(ErrorCode.SUCCESS, "成功", exam.getId());
        } else {
            res = new CommonData(ErrorCode.INSERT_FAILED, "插入失败");
        }

        return JSON.toJSONString(res);
    }

    @PostMapping("/addNewExam")
    public String addNewExam(Authentication authentication) {
//        JSONObject body = JSON.parseObject(requestBody);
        QuestionBank bank = new QuestionBank();
        bank.setAuthor(((User) authentication.getPrincipal()).getId());
        bank.setName("考试题库");
        bank.setPrivate(true);
        bank.setCreationTime(new Date());

        questionService.addBank(bank);

        Exam exam = new Exam();
        exam.setTitle("新考试");
        exam.setSubtitle("新考试");
        exam.setEarliestStartTime(new Date(new Date().getTime() + 86400000));
        exam.setLatestStartTime(new Date(new Date().getTime() + 86400000 + 1800000));
        exam.setDuration(3600000L);
        exam.setBankId(bank.getId());
        exam.setType("fixed");
        exam.setOrderJson("{\"part\": []}");
        exam.setRepeatTime(1);
        exam.setCalGradeAtOnce(true);
        exam.setFullMark(0f);

        Integer suc = examService.addExam(exam);

        CommonData res;
        if (suc > 0) {
            res = new CommonData(ErrorCode.SUCCESS, "成功", exam.getId());
        } else {
            res = new CommonData(ErrorCode.INSERT_FAILED, "插入失败");
        }

        return JSON.toJSONString(res);
    }

    @PostMapping("/deleteExam")
    public String deleteExam(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        Integer id=body.getInteger("id");

        Integer suc=examService.deleteExam(id);

        CommonData res;
        if (suc > 0) {
            res = new CommonData(ErrorCode.SUCCESS, "成功");
        } else {
            res = new CommonData(ErrorCode.DELETE_FAILED, "删除失败");
        }

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

    private boolean isExamOver(Exam exam, ExamPaper examPaper) {
        return examPaper.getFinishTime() != null ||
                new Date().compareTo(new Date(examPaper.getStartTime().getTime() + exam.getDuration())) > 0;
    }

    private boolean isExamTimeOut(Exam exam) {
        return new Date().compareTo(new Date(exam.getLatestStartTime().getTime() + exam.getDuration())) > 0;
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
