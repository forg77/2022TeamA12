package edu.zstu.examsys.mapper;

import edu.zstu.examsys.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface UserMapper {
    User getUserByUsername(String username);
    Integer addUser(User user);
}
