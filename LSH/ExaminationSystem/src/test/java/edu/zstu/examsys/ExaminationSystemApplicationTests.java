package edu.zstu.examsys;

import com.alibaba.fastjson.JSON;
import edu.zstu.examsys.mapper.ExamCorrectMapper;
import edu.zstu.examsys.pojo.*;
import edu.zstu.examsys.service.AlgorithmService;
import edu.zstu.examsys.service.QuestionService;
import org.apache.tomcat.util.buf.Utf8Decoder;
import org.apache.tomcat.util.buf.Utf8Encoder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SpringBootTest
class ExaminationSystemApplicationTests {

    @Test
    void contextLoads() {

    }

    @Autowired
    ExamCorrectMapper examCorrectMapper;

    @Test
    void test() {
//        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
//        System.out.println(encoder.encode("123"));
        List<CorrectInfo> list = new LinkedList<>();
        CorrectInfo info = new CorrectInfo();
        info.setCorrectorId(1);
        info.setPaperId(12);
        list.add(info);
        info = new CorrectInfo();
        info.setCorrectorId(1);
        info.setPaperId(13);
        list.add(info);
        examCorrectMapper.addCorrectInfo(list);
    }

    @Test
    void supervisionTest() {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 9000);
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, new Utf8Encoder());
            HashMap<String, Object> send = new HashMap<>();
//            List<String> paths = new LinkedList<>();
//            paths.add("D:\\python1\\GazeTracking-master\\GazeTracking-master\\imgs\\1.jpeg");
//            paths.add("D:\\python1\\GazeTracking-master\\GazeTracking-master\\imgs\\2.jpeg");
//            paths.add("D:\\python1\\GazeTracking-master\\GazeTracking-master\\imgs\\3.jpeg");
//            paths.add("D:\\python1\\GazeTracking-master\\GazeTracking-master\\imgs\\4.jpg");
//            paths.add("D:\\python1\\GazeTracking-master\\GazeTracking-master\\imgs\\5.jpg");
//            paths.add("D:\\python1\\GazeTracking-master\\GazeTracking-master\\imgs\\6.jpg");
//            paths.add("D:\\python1\\GazeTracking-master\\GazeTracking-master\\imgs\\7.jpg");

            send.put("path", "D:\\python1\\GazeTracking-master\\GazeTracking-master\\imgs\\11.jpg");
            osw.append(JSON.toJSONString(send));
            osw.flush();

            InputStream inputStream = socket.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream, new Utf8Decoder());
            char[] buffer = new char[4096];
            int read = reader.read(buffer, 0, 4096);
            String response = new String(buffer, 0, read);
            System.out.println(response);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void searchTest() {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 9001);
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, new Utf8Encoder());
//            HashMap<String, Object> send = new HashMap<>();
//            List<String> paths = new LinkedList<>();
//            paths.add("D:\\python1\\GazeTracking-master\\GazeTracking-master\\imgs\\1.jpeg");
//            paths.add("D:\\python1\\GazeTracking-master\\GazeTracking-master\\imgs\\2.jpeg");
//            paths.add("D:\\python1\\GazeTracking-master\\GazeTracking-master\\imgs\\3.jpeg");
//            paths.add("D:\\python1\\GazeTracking-master\\GazeTracking-master\\imgs\\4.jpg");
//            paths.add("D:\\python1\\GazeTracking-master\\GazeTracking-master\\imgs\\5.jpg");
//            paths.add("D:\\python1\\GazeTracking-master\\GazeTracking-master\\imgs\\6.jpg");
//            paths.add("D:\\python1\\GazeTracking-master\\GazeTracking-master\\imgs\\7.jpg");

//            send.put("path", "D:\\python1\\GazeTracking-master\\GazeTracking-master\\imgs\\11.jpg");
//            osw.append("adel {\"content\":\"所以对于俄军来说，顿涅茨克地区是最难攻克的一个地方，俄军要想拿下此地务必要付出很大的代价。\",\"id\":20014}");
//            osw.append("qery [{\"content\":\"哪里最难攻克\"}]");
            osw.append("scor1 {\"content\":\"地区顿涅茨克是最难攻克的一个地方\",\"answer\":\"顿涅茨克地区是最难攻克的一个地方\"}");
            osw.flush();

            InputStream inputStream = socket.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream, new Utf8Decoder());
            char[] buffer = new char[4096];
            int read = reader.read(buffer, 0, 4096);
            String response = new String(buffer, 0, read);
            System.out.println(response);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    QuestionService questionService;

    @Test
    void getSearchList(){

        List<Question> questions = questionService.getQuestions(null, null, new Condition());
        for (Question question : questions) {
            System.out.println(question.getId());
        }
    }

//    @Test
//    void requestTest() {
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
//        String url = "http://139.9.58.231:8080/user/userInfo";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        //headers.add("Cookie","JSESSIONID=F248DCAB13DC766B9C35B646C13ECAB7");
//        //HttpEntity<String> request = new HttpEntity<>("{}",headers);
//        //HttpEntity<String> request = new HttpEntity<>("{\"houseId\":1,\"name\":\"村庄33\"}", headers);//("{\"text\":" + "\"百度是一家高科技公司\"" + "}", headers);
//        Map<String, Object> map = new HashMap<>();
//        map.put("username", "admin1");
//        map.put("password", "123");
////        map.put("emergency", true);
////        map.put("type", 0);
////        map.put("receiver_id", 1);
////        map.put("address", "add1");
////        map.put("status", 1);
////        map.put("order_time", "2021/09/05 18:00:00");
//        HttpEntity<String> request = new HttpEntity<>(JSON.toJSONString(map), headers);
//        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
//
//        System.out.println(response.getBody());
//    }
}
