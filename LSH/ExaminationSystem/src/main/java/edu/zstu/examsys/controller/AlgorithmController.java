package edu.zstu.examsys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edu.zstu.examsys.pojo.*;
import edu.zstu.examsys.service.AlgorithmService;
import edu.zstu.examsys.service.QuestionService;
import edu.zstu.examsys.util.JSONUtils;
import org.apache.tomcat.util.buf.Utf8Decoder;
import org.apache.tomcat.util.buf.Utf8Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/algorithm")
public class AlgorithmController {
    private static final Logger logger = LoggerFactory.getLogger(AlgorithmController.class);

    private AlgorithmService algorithmService;

    @Autowired
    public void setAlgorithmService(AlgorithmService algorithmService) {
        this.algorithmService = algorithmService;
    }

    private QuestionService questionService;

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/test")
    public void test() {
        try {
            Socket socket = new Socket("localhost", 38324);
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, new Utf8Encoder());
            osw.append("普京说了什么");
            System.out.println("abc");
            osw.flush();
            socket.shutdownOutput();

            socket.close();
            osw.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/search")
    public String search(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        Integer bankId = body.getInteger("bankId");
        String content = body.getString("search");
        Condition con = JSONUtils.setCondition(body);

        List<Integer> ids = null;
        if (content != null && !content.equals(""))
            ids = JSON.parseArray(algorithmService.search(content)).toJavaList(Integer.class);
        Map<String, Object> data = new HashMap<>();
        data.put("data", questionService.getQuestionsByIds(bankId, ids, con));
        data.put("count", questionService.getQuestionsByIdsCount(bankId, ids));

        return JSON.toJSONString(new CommonData(ErrorCode.SUCCESS, "成功", data));
    }

    @PostMapping("/updateSearchList")
    public String updateSearchList() {
        List<Question> questions = questionService.getQuestions(null, null, new Condition());
        for (Question question : questions) {
            algorithmService.addQuestion(question.getId(), question.getDescription());
        }

        return JSON.toJSONString(new CommonData(ErrorCode.SUCCESS, "成功"));
    }

    @PostMapping("/getSimilarity")
    public String getSimilarity(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        String content = body.getString("content");
        String answer = body.getString("answer");

        return JSON.toJSONString(new CommonData(ErrorCode.SUCCESS, "成功", JSON.parse(algorithmService.getSimilarity(answer, content))));
    }
}
