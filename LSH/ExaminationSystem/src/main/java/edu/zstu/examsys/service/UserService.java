package edu.zstu.examsys.service;

import edu.zstu.examsys.mapper.UserMapper;
import edu.zstu.examsys.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    public Integer addUser(User user) {
        if (user.getRegisterTime() == null)
            user.setRegisterTime(new Date());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.addUser(user);
    }
}
