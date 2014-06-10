package com.active.presentation.ox.domain;

import com.active.presentation.core.domain.Speaker;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/10/14
 */
@Entity
public class OxDashBoard {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Speaker speaker;

    private Date createdDate;
    private String title;
    private Boolean status;

    public OxDashBoard() {}

    public OxDashBoard(Speaker speaker, String title, Boolean status) {
        this.speaker = speaker;
        this.title = title;
        this.status = status;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OxDashBoard{" +
                "id=" + id +
                ", speaker=" + speaker +
                ", createdDate=" + createdDate +
                ", title='" + title + '\'' +
                ", status=" + status +
                '}';
    }
}
