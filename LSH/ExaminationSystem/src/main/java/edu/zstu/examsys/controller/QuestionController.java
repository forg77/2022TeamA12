package edu.zstu.examsys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import edu.zstu.examsys.pojo.CommonData;
import edu.zstu.examsys.pojo.Condition;
import edu.zstu.examsys.pojo.ErrorCode;
import edu.zstu.examsys.service.QuestionService;
import edu.zstu.examsys.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
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

        Map<String, Object> data = new HashMap<>();
        data.put("count", questionService.getBanksCount(author));
        data.put("data", questionService.getBanks(author, con));

        CommonData res = new CommonData(ErrorCode.SUCCESS, "成功", data);

        return JSON.toJSONString(res);
    }

    @PostMapping("/getQuestions")
    public String getQuestions(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        Condition con = JSONUtils.setCondition(body);
        Integer bankId = body.getInteger("bankId");

        Map<String, Object> data = new HashMap<>();
        data.put("count", questionService.getQuestionsCount(bankId));
        data.put("data", questionService.getQuestions(bankId, con));

        CommonData res = new CommonData(ErrorCode.SUCCESS, "成功", data);

        return JSON.toJSONString(res);
    }
}
