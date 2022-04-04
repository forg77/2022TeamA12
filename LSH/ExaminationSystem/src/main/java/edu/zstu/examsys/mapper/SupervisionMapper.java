package edu.zstu.examsys.mapper;

import edu.zstu.examsys.pojo.Condition;
import edu.zstu.examsys.pojo.CorrectInfo;
import edu.zstu.examsys.pojo.Supervision;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface SupervisionMapper {
    List<Supervision> getSupervisionInfos(Integer examId, Integer studentId, Boolean warn, Condition con);

    Integer addSupervisionInfo(Supervision info);
}
