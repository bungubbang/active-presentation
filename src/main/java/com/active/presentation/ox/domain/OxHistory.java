package com.active.presentation.ox.domain;

import com.active.presentation.core.domain.Audience;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/10/14
 */
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"ox_dash_board_id", "audience_id"}))
public class OxHistory implements Serializable {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private OxDashBoard oxDashBoard;

    @OneToOne
    private Audience audience;

    private String result;
    private Date createdDate = new Date();
    private Date modifyDate = new Date();

    public OxHistory() {}

    public OxHistory(OxDashBoard oxDashBoard, Audience audience, String result) {
        this.oxDashBoard = oxDashBoard;
        this.audience = audience;
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OxDashBoard getOxDashBoard() {
        return oxDashBoard;
    }

    public void setOxDashBoard(OxDashBoard oxDashBoard) {
        this.oxDashBoard = oxDashBoard;
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
        return "OxHistory{" +
                "id=" + id +
                ", oxDashBoard=" + oxDashBoard +
                ", audience=" + audience +
                ", result='" + result + '\'' +
                ", createdDate=" + createdDate +
                ", modifyDate=" + modifyDate +
                '}';
    }
}
