package com.active.presentation.domain;

import javax.persistence.*;
import javax.persistence.criteria.Predicate;
import javax.validation.constraints.Max;
import java.util.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    private Speaker speaker;

    @Enumerated(EnumType.STRING)
    private PresentationType presentationType;

    private Date createdDate = new Date();
    private String title;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Question> questions = new ArrayList<Question>();

    @Max(10)
    private Integer choiceCount;

    private Boolean status = true;
    private Boolean secure = false;
    private Boolean anonymous = false;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Tag> tags = new LinkedHashSet<Tag>();

    public PresentationDashboard() {}

    public PresentationDashboard(PresentationType presentationType, String title, Boolean status, Boolean secure, Boolean anonymous) {
        this.presentationType = presentationType;
        this.title = title;
        this.status = status;
        this.secure = secure;
        this.anonymous = anonymous;
    }

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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
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

    public Boolean getSecure() {
        return secure;
    }

    public void setSecure(Boolean secure) {
        this.secure = secure;
    }

    public Boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "PresentationDashboard{" +
                "id=" + id +
                ", speaker=" + speaker +
                ", presentationType=" + presentationType +
                ", createdDate=" + createdDate +
                ", title='" + title + '\'' +
                ", questions=" + questions +
                ", choiceCount=" + choiceCount +
                ", status=" + status +
                ", secure=" + secure +
                ", anonymous=" + anonymous +
                ", tags=" + tags +
                '}';
    }
}
