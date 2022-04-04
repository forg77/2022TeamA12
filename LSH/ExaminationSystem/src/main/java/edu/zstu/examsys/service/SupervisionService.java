package edu.zstu.examsys.service;

import edu.zstu.examsys.mapper.SupervisionMapper;
import edu.zstu.examsys.pojo.Condition;
import edu.zstu.examsys.pojo.Supervision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SupervisionService {
    private SupervisionMapper supervisionMapper;

    @Autowired
    public void setSupervisionMapper(SupervisionMapper supervisionMapper) {
        this.supervisionMapper = supervisionMapper;
    }

    public List<Supervision> getSupervisionInfos(Integer examId, Integer studentId, Boolean warn, Condition con) {
        return supervisionMapper.getSupervisionInfos(examId, studentId, warn, con);
    }

    public Integer addSupervisionInfo(Supervision info) {
        return supervisionMapper.addSupervisionInfo(info);
    }
}
