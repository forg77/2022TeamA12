package edu.zstu.examsys.controller;

import com.alibaba.fastjson.JSON;
import edu.zstu.examsys.mapper.TestMapper;
import edu.zstu.examsys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
        return authentication.getAuthorities().toString();
    }


}
