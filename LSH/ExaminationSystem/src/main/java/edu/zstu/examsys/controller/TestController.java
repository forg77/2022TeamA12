package edu.zstu.examsys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import edu.zstu.examsys.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    TestMapper testMapper;

    @RequestMapping("/test")
    public String Test(@RequestBody String requestBody, HttpServletResponse res) {
        JSONObject body = JSON.parseObject(requestBody);
        Integer offset = body.getInteger("offset");
        Integer max = body.getInteger("max");
        String order = body.getString("order");
        Boolean desc = body.getBoolean("desc");

        Map<String, Object> result = new HashMap<>();
        result.put("count", testMapper.getCount());
        result.put("data", testMapper.getAll(offset, max, order, desc));
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        res.setStatus(HttpStatus.BAD_REQUEST.value());
        return JSON.toJSONString(result);
    }
}
