package edu.zstu.examsys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import edu.zstu.examsys.pojo.Condition;
import edu.zstu.examsys.service.QuestionService;
import edu.zstu.examsys.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/question")
public class QuestionController {
    private QuestionService questionService;

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/getBanks")
    public String getBanks(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        Condition con = JSONUtils.setCondition(body);
        Integer author = body.getInteger("author");

        return JSON.toJSONString(questionService.getBanks(author, con));
    }
}
