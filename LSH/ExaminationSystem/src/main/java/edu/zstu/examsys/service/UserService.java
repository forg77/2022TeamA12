package edu.zstu.examsys.service;

import edu.zstu.examsys.mapper.UserMapper;
import edu.zstu.examsys.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User getUserByUsername(String username){
        return userMapper.getUserByUsername(username);
    }
}
