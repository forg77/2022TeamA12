package edu.zstu.examsys.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import edu.zstu.examsys.controller.AlgorithmController;
import edu.zstu.examsys.pojo.Supervision;
import org.apache.tomcat.util.buf.Utf8Decoder;
import org.apache.tomcat.util.buf.Utf8Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class AlgorithmService {
    private static final Logger logger = LoggerFactory.getLogger(AlgorithmService.class);

    private String sendCommand(String command, String content) {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 9001);
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, new Utf8Encoder());

            osw.append(command).append(" ").append(content);
            osw.flush();

            InputStream inputStream = socket.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream, new Utf8Decoder());
            char[] buffer = new char[4096];
            int read = reader.read(buffer, 0, 4096);

//            JSONObject res = JSON.parseObject(new String(buffer, 0, read));
            return new String(buffer, 0, read);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String search(String content) {
        HashMap<String, Object> send = new HashMap<>();
        send.put("content", content);
        List<HashMap<String, Object>> list = new LinkedList<>();
        list.add(send);
        return sendCommand("qery", JSON.toJSONString(list));
    }

    public String addQuestion(Integer id,String content) {
        HashMap<String, Object> send = new HashMap<>();
        send.put("id", id);
        send.put("content", content);

        return sendCommand("adel", JSON.toJSONString(send));
    }


    public String getSimilarity(String answer, String content) {
        HashMap<String, Object> send = new HashMap<>();
        send.put("content", content);
        send.put("answer", answer);
        return sendCommand("scor", JSON.toJSONString(send));
    }


}
