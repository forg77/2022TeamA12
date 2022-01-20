package edu.zstu.examsys.controller;

import com.alibaba.fastjson.JSON;
import edu.zstu.examsys.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    TestMapper testMapper;

    @RequestMapping("/test")
    public String Test() {
        List<Map<String, Object>> all = testMapper.getAll();
        return JSON.toJSONString(all);
    }
}
