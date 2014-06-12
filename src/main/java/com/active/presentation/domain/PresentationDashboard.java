package com.active.presentation.domain;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.util.Date;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/11/14
 */
@Entity
public class PresentationDashboard {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Speaker speaker;

    @Enumerated(EnumType.STRING)
    private PresentationType presentationType;

    private Date createdDate;
    private String title;
    private String questions;

    @Max(10)
    private Integer choiceCount;
    private Boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    public PresentationType getPresentationType() {
        return presentationType;
    }

    public void setPresentationType(PresentationType presentationType) {
        this.presentationType = presentationType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public Integer getChoiceCount() {
        return choiceCount;
    }

    public void setChoiceCount(Integer choiceCount) {
        this.choiceCount = choiceCount;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PresentationDashboard{" +
                "id=" + id +
                ", speaker=" + speaker +
                ", presentationType=" + presentationType +
                ", createdDate=" + createdDate +
                ", title='" + title + '\'' +
                ", questions='" + questions + '\'' +
                ", choiceCount=" + choiceCount +
                ", status=" + status +
                '}';
    }
}
