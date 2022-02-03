package edu.zstu.examsys.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface TestMapper {
    List<Map<String,Object>> getAll(Integer offset, Integer max, String order, Boolean desc);
    Integer getCount();
}
