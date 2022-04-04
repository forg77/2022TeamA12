package edu.zstu.examsys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import edu.zstu.examsys.pojo.CommonData;
import edu.zstu.examsys.pojo.ErrorCode;
import edu.zstu.examsys.pojo.Supervision;
import edu.zstu.examsys.service.SupervisionService;
import org.apache.log4j.Level;
import org.apache.tomcat.util.buf.Utf8Decoder;
import org.apache.tomcat.util.buf.Utf8Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/supervision")
public class SupervisionController {
    private static final Logger logger = LoggerFactory.getLogger(SupervisionController.class);

    private SupervisionService supervisionService;

    @Autowired
    public void setSupervisionService(SupervisionService supervisionService) {
        this.supervisionService = supervisionService;
    }

    @PostMapping("/uploadImg")
    public String uploadImg(@RequestParam(value = "file") MultipartFile file, Integer examId, Integer userId) {
        if (file.isEmpty()) {
            new CommonData(ErrorCode.EMPTY_FILE, "文件为空");
        }
        String runPath;
        try {
            runPath = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
            return JSON.toJSONString(new CommonData(ErrorCode.SERVER_FILE_ERROR, "服务器文件错误"));
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = runPath + "/supervision/" + examId + "/"; // 上传后的路径
        fileName = userId + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            boolean mkdirs = dest.getParentFile().mkdirs();
            if (!mkdirs) {
                return JSON.toJSONString(new CommonData(ErrorCode.SERVER_FILE_ERROR, "服务器文件错误"));
            }
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //创建Socket，获取监考数据
        Socket socket = null;
        try {
            socket = new Socket("localhost", 9000);
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, new Utf8Encoder());
            HashMap<String, Object> send = new HashMap<>();

            send.put("path", filePath + fileName);
            osw.append(JSON.toJSONString(send));
            osw.flush();

            InputStream inputStream = socket.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream, new Utf8Decoder());
            char[] buffer = new char[4096];
            int read = reader.read(buffer, 0, 4096);
//            String response = ;
//            System.out.println(response);
            JSONObject res = JSON.parseObject(new String(buffer, 0, read));
            logger.info("text:" + res.getString("text") + ",warn:" + res.getBoolean("warning"));
            logger.info("path:" + filePath + fileName);
            supervisionService.addSupervisionInfo(new Supervision() {{
                setExamId(examId);
                setStudentId(userId);
                setTime(new Date());
                setText(res.getString("text"));
                setWarn(res.getBoolean("warning"));
            }});

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return JSON.toJSONString(new CommonData(ErrorCode.SUCCESS, "上传成功"));
    }
}
