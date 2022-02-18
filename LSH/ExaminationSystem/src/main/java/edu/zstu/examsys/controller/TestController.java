package edu.zstu.examsys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edu.zstu.examsys.mapper.TestMapper;
import edu.zstu.examsys.pojo.CommonData;
import edu.zstu.examsys.pojo.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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

//        System.out.println(requestBody);

        Map<String, Object> data = new HashMap<>();
        data.put("count", testMapper.getCount());
        data.put("data", testMapper.getAll(offset, max, order, desc));

        CommonData result = new CommonData(ErrorCode.SUCCESS, "成功", data);
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        res.setStatus(HttpStatus.BAD_REQUEST.value());
        return JSON.toJSONString(result);
    }

    @RequestMapping("/update")
    public String Update(@RequestBody String requestBody) {
        Map<String, Object> result = new HashMap<>();
        try {
            JSONObject body = JSON.parseObject(requestBody);
            Integer id = body.getInteger("id");
            Integer a = body.getInteger("a");
            String b = body.getString("b");
            String c = body.getString("c");

            int suc = testMapper.update(id, a, b, c);

            if (suc > 0) {
                result.put("errorCode", 0);
            } else {
                result.put("errorCode", 1);
            }
        } catch (Exception e) {
            result.put("errorCode", 2);
            return JSON.toJSONString(result);
        }
        return JSON.toJSONString(result);
    }

    @RequestMapping("/add")
    public String Add(@RequestBody String requestBody) {
        Map<String, Object> result = new HashMap<>();
        try {
            JSONObject body = JSON.parseObject(requestBody);
            Integer a = body.getInteger("a");
            String b = body.getString("b");
            String c = body.getString("c");
            int suc = testMapper.add(a, b, c);

            if (suc > 0) {
                result.put("errorCode", 0);
            } else {
                result.put("errorCode", 1);
            }
        } catch (Exception e) {
            result.put("errorCode", 2);
            return JSON.toJSONString(result);
        }
        return JSON.toJSONString(result);
    }

    @RequestMapping("/delete")
    public String Delete(@RequestBody String requestBody) {
        Map<String, Object> result = new HashMap<>();
        try {
            JSONObject body = JSON.parseObject(requestBody);
            List<Integer> itemsId = JSONObject.parseArray(body.getJSONArray("itemsId").toJSONString(), Integer.class);

            int suc = testMapper.delete(itemsId);

            if (suc > 0) {
                result.put("errorCode", 0);
            } else {
                result.put("errorCode", 1);
            }
        } catch (Exception e) {
            result.put("errorCode", 2);
            return JSON.toJSONString(result);
        }
        return JSON.toJSONString(result);
    }
}
