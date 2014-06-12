package com.active.presentation.domain;

import com.active.presentation.ox.domain.OxDashBoard;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/11/14
 */
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"dashboard_id", "audience_id"}))
public class Answer {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private PresentationDashboard dashboard;

    @OneToOne
    private Audience audience;

    private String result;
    private Date createdDate = new Date();
    private Date modifyDate = new Date();

    public Answer() {}

    public Answer(PresentationDashboard dashboard, Audience audience, String result) {
        this.dashboard = dashboard;
        this.audience = audience;
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PresentationDashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(PresentationDashboard dashboard) {
        this.dashboard = dashboard;
    }

    public Audience getAudience() {
        return audience;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", dashboard=" + dashboard +
                ", audience=" + audience +
                ", result='" + result + '\'' +
                ", createdDate=" + createdDate +
                ", modifyDate=" + modifyDate +
                '}';
    }
}
