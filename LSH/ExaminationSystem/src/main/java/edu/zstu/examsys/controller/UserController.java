package edu.zstu.examsys.controller;

import com.alibaba.fastjson.JSON;
import edu.zstu.examsys.mapper.TestMapper;
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
        Map<String, Object> result = new HashMap<>();
        result.put("errCode", 101);
        result.put("errMsg", "请先登录");

//        res.setContentType("application/json;charset=UTF-8");
        return JSON.toJSONString(result);
    }

    //获取当前用户基本信息
    @RequestMapping("/userInfo")
    public String userInfo(Authentication authentication) {
        Map<String, Object> result = new HashMap<>();

        User user = (User) authentication.getPrincipal();

        result.put("id", user.getId());
        result.put("username", user.getUsername());
        result.put("nickname", user.getNickname());

        return JSON.toJSONString(result);
    }
}
