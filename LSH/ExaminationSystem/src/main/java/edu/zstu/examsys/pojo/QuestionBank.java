package edu.zstu.examsys.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class QuestionBank {
    private Integer id;
    private String name;
    private Integer author;
    private String authorName;
    private Date creationTime;
    private Boolean isPrivate;
    private Integer count;
    private Integer examId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    @Override
    public String toString() {
        return "QuestionBank{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author=" + author +
                ", authorName='" + authorName + '\'' +
                ", creationTime=" + creationTime +
                ", isPrivate=" + isPrivate +
                ", count=" + count +
                ", examId=" + examId +
                '}';
    }
}
