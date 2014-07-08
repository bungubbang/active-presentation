package com.active.presentation.controller.admin.form;

import com.active.presentation.domain.PresentationDashboard;
import com.active.presentation.domain.PresentationType;
import com.active.presentation.domain.Question;
import com.active.presentation.domain.Speaker;

import java.util.Date;
import java.util.List;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 7/8/14
 */
public class BoardMakeForm {
    private String questionList;
    private String title;
    private Boolean secure;
    private Boolean status;
    private PresentationType presentationType;

    public String getQuestionList() {
        return questionList;
    }

    public void setQuestionList(String questionList) {
        this.questionList = questionList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getSecure() {
        return secure;
    }

    public void setSecure(Boolean secure) {
        this.secure = secure;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public PresentationType getPresentationType() {
        return presentationType;
    }

    public void setPresentationType(PresentationType presentationType) {
        this.presentationType = presentationType;
    }

    @Override
    public String toString() {
        return "BoardMakeForm{" +
                "questionList='" + questionList + '\'' +
                ", title='" + title + '\'' +
                ", secure=" + secure +
                ", status=" + status +
                ", presentationType=" + presentationType +
                '}';
    }
}
