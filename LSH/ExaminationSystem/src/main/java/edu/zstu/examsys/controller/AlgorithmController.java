package edu.zstu.examsys.controller;

import org.apache.tomcat.util.buf.Utf8Encoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

@RestController
@RequestMapping("/algorithm")
public class AlgorithmController {
    @PostMapping("/test")
    public void test() {
        try {
            Socket socket = new Socket("localhost", 38324);
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw=new OutputStreamWriter(os,new Utf8Encoder());
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


}
