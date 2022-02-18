package edu.zstu.examsys.controller;

import com.alibaba.fastjson.JSON;
import edu.zstu.examsys.mapper.TestMapper;
import edu.zstu.examsys.pojo.CommonData;
import edu.zstu.examsys.pojo.ErrorCode;
import edu.zstu.examsys.pojo.User;
import edu.zstu.examsys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/test")
    public String test(HttpServletRequest req, Authentication authentication) {
        System.out.println(authentication.getAuthorities());
        return authentication.getPrincipal().toString();
    }

    @GetMapping("/needLogin")
    public String needLogin() {
        CommonData res = new CommonData(ErrorCode.NO_LOGIN, "请先登录");

        return JSON.toJSONString(res);
    }

    //获取当前用户基本信息
    @RequestMapping("/userInfo")
    public String userInfo(Authentication authentication) {
        Map<String, Object> data = new HashMap<>();

        User user = (User) authentication.getPrincipal();

        data.put("id", user.getId());
        data.put("username", user.getUsername());
        data.put("nickname", user.getNickname());

        CommonData res = new CommonData(ErrorCode.SUCCESS, "成功", data);

        return JSON.toJSONString(res);
    }
}
