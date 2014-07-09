package com.active.presentation.controller.admin.form;


import javax.validation.constraints.NotNull;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 7/4/14
 */
public class BoardModifyForm {
    @NotNull
    private Long id;
    private String title;
    private boolean secure;
    private boolean status;

    private String questionList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getQuestionList() {
        return questionList;
    }

    public void setQuestionList(String questionList) {
        this.questionList = questionList;
    }

    @Override
    public String toString() {
        return "BoardModifyForm{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", secure=" + secure +
                ", status=" + status +
                ", questionList='" + questionList + '\'' +
                '}';
    }
}
