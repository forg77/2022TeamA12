package edu.zstu.examsys.pojo;

import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Date;

public class UserAuthentication implements AuthenticationInfo {
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private Date registerTime;
    private String permission;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
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
        return id;
    }

    @Override
    public void setUserId(Integer userId) {
        id = userId;
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
        return "UserAuthentication{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", registerTime=" + registerTime +
                ", permission='" + permission + '\'' +
                ", authenticationId=" + authenticationId +
                ", type='" + type + '\'' +
                ", realName='" + realName + '\'' +
                ", number='" + number + '\'' +
                ", school='" + school + '\'' +
                ", college='" + college + '\'' +
                ", admissionTime=" + admissionTime +
                ", clazz='" + clazz + '\'' +
                ", grade='" + grade + '\'' +
                ", major='" + major + '\'' +
                '}';
    }
}
