package com.active.presentation.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 2014. 7. 24.
 */
@Entity
public class DashboardGroup {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private Date createdDate = new Date();
    private Date modifyDate = new Date();

    @ManyToOne
    private Speaker speaker;

    public DashboardGroup() {
    }

    public DashboardGroup(Speaker speaker, String title) {
        this.speaker = speaker;
        this.title = title;
    }

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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    @Override
    public String toString() {
        return "DashboardGroup{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DashboardGroup)) return false;

        DashboardGroup group = (DashboardGroup) o;

        if (id != null ? !id.equals(group.id) : group.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
