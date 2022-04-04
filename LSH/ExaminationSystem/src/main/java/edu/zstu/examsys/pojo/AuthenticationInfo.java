package edu.zstu.examsys.pojo;

import java.util.Date;

public interface AuthenticationInfo {
    Integer getAuthenticationId();

    void setAuthenticationId(Integer id);

    Integer getUserId();

    void setUserId(Integer userId);

    String getType();

    void setType(String type);

    String getNumber();

    void setNumber(String number);

    String getSchool();

    void setSchool(String school);

    String getCollege();

    void setCollege(String college);

    Date getAdmissionTime();

    void setAdmissionTime(Date admissionTime);
}
