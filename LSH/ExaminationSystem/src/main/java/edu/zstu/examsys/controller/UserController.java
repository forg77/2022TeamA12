package edu.zstu.examsys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import edu.zstu.examsys.mapper.TestMapper;
import edu.zstu.examsys.pojo.CommonData;
import edu.zstu.examsys.pojo.ErrorCode;
import edu.zstu.examsys.pojo.User;
import edu.zstu.examsys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
        data.put("permission", user.getPermission());

        CommonData res = new CommonData(ErrorCode.SUCCESS, "成功", data);

        return JSON.toJSONString(res);
    }

    @PostMapping("/register")
    public String register(@RequestBody String requestBody) {
        JSONObject body = JSON.parseObject(requestBody);
        User user = body.toJavaObject(User.class);

        User exist= userService.getUserByUsername(user.getUsername());
        if(exist!=null){
            return JSON.toJSONString(new CommonData(ErrorCode.USER_ALREADY_EXISTS, "用户已存在", user.getId()));
        }

        Integer suc= userService.addUser(user);

        CommonData res;
        if(suc>0) {
            res = new CommonData(ErrorCode.SUCCESS, "成功", user.getId());
        }else{
            res = new CommonData(ErrorCode.UNKNOWN_ERROR, "未知错误");
        }

        return JSON.toJSONString(res);
    }
}
