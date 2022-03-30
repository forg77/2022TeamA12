package edu.zstu.examsys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import edu.zstu.examsys.pojo.*;
import edu.zstu.examsys.service.QuestionService;
import edu.zstu.examsys.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/question")
public class QuestionController {
    private QuestionService questionService;

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/getBanks")
    public String getBanks(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        Condition con = JSONUtils.setCondition(body);
        Integer author = body.getInteger("author");
        String search = body.getString("search");

        Map<String, Object> data = new HashMap<>();
        data.put("count", questionService.getBanksCount(author, search));
        data.put("data", questionService.getBanks(null, author, search, con));

        CommonData res = new CommonData(ErrorCode.SUCCESS, "成功", data);

        return JSON.toJSONString(res);
    }

    @PostMapping("/getQuestions")
    public String getQuestions(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        Condition con = JSONUtils.setCondition(body);
        Integer bankId = body.getInteger("bankId");
        String search = body.getString("search");

        Map<String, Object> data = new HashMap<>();
        data.put("count", questionService.getQuestionsCount(bankId, search));
        data.put("data", questionService.getQuestions(bankId, search, con));

        CommonData res = new CommonData(ErrorCode.SUCCESS, "成功", data);

        return JSON.toJSONString(res);
    }

    @PostMapping("/getAllQuestions")
    public String getAllQuestions(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        Condition con = JSONUtils.setCondition(body);
        Integer bankId = body.getInteger("bankId");

        Map<String, Object> data = new HashMap<>();
        data.put("choice", questionService.getChoiceQuestions(null, bankId, null, con));
        data.put("normal", questionService.getNormalQuestions(null, bankId, null, con));

        CommonData res = new CommonData(ErrorCode.SUCCESS, "成功", data);

        return JSON.toJSONString(res);
    }

    @PostMapping("/getQuestion")
    public String getQuestion(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        Integer id = body.getInteger("id");

        String questionType = questionService.getQuestionType(id);
        Question question = null;
        if ("choice".equals(questionType) || "multi_choice".equals(questionType)) {
            question = questionService.getChoiceQuestions(id, null, null, new Condition()).get(0);
        } else if ("completion".equals(questionType) || "short_answer".equals(questionType)) {
            question = questionService.getNormalQuestions(id, null, null, new Condition()).get(0);
        }

        CommonData res = new CommonData(ErrorCode.SUCCESS, "成功", question);

        return JSON.toJSONString(res);
    }

    @PostMapping("/addQuestion")
    @Transactional
    public String addQuestion(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        String type = body.getString("type");
        Integer id = body.getInteger("id");
        Integer bankId = body.getInteger("bankId");

        String typeBefore = questionService.getQuestionType(id);
        if ("choice".equals(type) || "multi_choice".equals(type)) {
            ChoiceQuestion choiceQuestion = new ChoiceQuestion();
            choiceQuestion.setId(id);
            choiceQuestion.setType(type);
            choiceQuestion.setDescription(body.getString("description"));
            choiceQuestion.setBankId(bankId);
            choiceQuestion.setChoice(body.getString("choice"));
            choiceQuestion.setAnswer(body.getString("answer"));

            questionService.addQuestion(choiceQuestion);
            questionService.addChoiceQuestion(choiceQuestion);

            id = choiceQuestion.getId();
//            if (id == null || typeBefore == null) {
//                questionService.addQuestion(choiceQuestion);
//                questionService.addChoiceQuestion(choiceQuestion);
//            } else {
//                questionService.updateQuestion(choiceQuestion);
//                questionService.updateChoiceQuestion(choiceQuestion);
//            }
        } else if ("completion".equals(type) || "short_answer".equals(type)) {
            NormalQuestion normalQuestion = new NormalQuestion();
            normalQuestion.setId(id);
            normalQuestion.setType(type);
            normalQuestion.setDescription(body.getString("description"));
            normalQuestion.setBankId(bankId);
            normalQuestion.setAnswer(body.getString("answer"));

            questionService.addQuestion(normalQuestion);
            questionService.addNormalQuestion(normalQuestion);

            id = normalQuestion.getId();
//            if (id == null || typeBefore == null) {
//                questionService.addQuestion(normalQuestion);
//                questionService.addNormalQuestion(normalQuestion);
//            } else {
//                questionService.updateQuestion(normalQuestion);
//                questionService.updateNormalQuestion(normalQuestion);
//            }
        }

        if (typeBefore != null && !typeBefore.equals(type)) {
            if (typeBefore.equals("choice") || typeBefore.equals("multi_choice")) {
                questionService.deleteChoiceQuestion(id);
            } else if (typeBefore.equals("completion") || typeBefore.equals("short_answer")) {
                questionService.deleteNormalQuestion(id);
            }
        }

        return JSON.toJSONString(new CommonData(ErrorCode.SUCCESS, "成功", id));
    }

    @PostMapping("/deleteQuestion")
    public String deleteQuestion(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        Integer id = body.getInteger("id");
//        System.out.println(id);
//        String type = questionService.getQuestionType(id);
//        if ("choice".equals(type) || "multi_choice".equals(type)) {
//            questionService.deleteChoiceQuestion(id);
//        } else if ("completion".equals(type) || "short_answer".equals(type)) {
//            questionService.deleteNormalQuestion(id);
//        }
        questionService.deleteQuestion(id);

        return JSON.toJSONString(new CommonData(ErrorCode.SUCCESS, "成功"));
    }

    @PostMapping("/addBank")
    public String addBank(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        QuestionBank bank = body.toJavaObject(QuestionBank.class);

        Integer suc = questionService.addBank(bank);

        if (suc > 0)
            return JSON.toJSONString(new CommonData(ErrorCode.SUCCESS, "成功"));
        else
            return JSON.toJSONString(new CommonData(ErrorCode.INSERT_FAILED, "插入失败"));
    }
}
