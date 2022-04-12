package edu.zstu.examsys.pojo;

import java.util.Date;

public class Supervision implements AuthenticationInfo {
    private Integer id;
    private Integer examId;
    private Integer studentId;
    private Date time;
    private String text;
    private Boolean warn;

    private Integer authenticationId;
    private String type;
    private String realName;
    private String number;
    private String school;
    private String college;
    private Date admissionTime;
    private String clazz;
    private String grade;
    private String major;

//    private AuthenticationInfo authentication;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getWarn() {
        return warn;
    }

    public void setWarn(Boolean warn) {
        this.warn = warn;
    }

    @Override
    public Integer getAuthenticationId() {
        return authenticationId;
    }

    @Override
    public void setAuthenticationId(Integer authenticationId) {
        this.authenticationId = authenticationId;
    }

    @Override
    public Integer getUserId() {
        return studentId;
    }

    @Override
    public void setUserId(Integer userId) {
        studentId = userId;
    }

    @Override
    public String getRealName() {
        return realName;
    }

    @Override
    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String getSchool() {
        return school;
    }

    @Override
    public void setSchool(String school) {
        this.school = school;
    }

    @Override
    public String getCollege() {
        return college;
    }

    @Override
    public void setCollege(String college) {
        this.college = college;
    }

    @Override
    public Date getAdmissionTime() {
        return admissionTime;
    }

    @Override
    public void setAdmissionTime(Date admissionTime) {
        this.admissionTime = admissionTime;
    }

    @Override
    public String getClazz() {
        return clazz;
    }

    @Override
    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getGrade() {
        return grade;
    }

    @Override
    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String getMajor() {
        return major;
    }

    @Override
    public void setMajor(String major) {
        this.major = major;
    }

    @Override
    public String toString() {
        return "Supervision{" +
                "id=" + id +
                ", examId=" + examId +
                ", studentId=" + studentId +
                ", time=" + time +
                ", text='" + text + '\'' +
                ", warn=" + warn +
                ", authenticationId=" + authenticationId +
                ", type='" + type + '\'' +
                ", realName='" + realName + '\'' +
                ", number='" + number + '\'' +
                ", school='" + school + '\'' +
                ", college='" + college + '\'' +
                ", admissionTime=" + admissionTime +
                '}';
    }
}
