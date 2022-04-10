package edu.zstu.examsys.pojo;

import java.util.Date;

public interface AuthenticationInfo {
    Integer getAuthenticationId();

    void setAuthenticationId(Integer id);

    Integer getUserId();

    void setUserId(Integer userId);

    String getType();

    void setRealName(String realName);

    String getRealName();

    void setType(String type);

    String getNumber();

    void setNumber(String number);

    String getSchool();

    void setSchool(String school);

    String getCollege();

    void setCollege(String college);

    Date getAdmissionTime();

    void setAdmissionTime(Date admissionTime);

    String getClazz();

    void setClazz(String clazz);

    String getGrade();

    void setGrade(String grade);

    String getMajor();

    void setMajor(String major);
}
