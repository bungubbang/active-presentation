package com.active.presentation.domain;

import javax.persistence.*;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/10/14
 */
@Entity
public class Audience {

    @Id @GeneratedValue
    private Long id;

    private String userKey;

    @Enumerated(EnumType.STRING)
    private AudienceType audienceType;

    @OneToOne(fetch = FetchType.LAZY)
    private Speaker speaker;

    public Audience() {}

    public Audience(String userKey, AudienceType audienceType) {
        this.userKey = userKey;
        this.audienceType = audienceType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public AudienceType getAudienceType() {
        return audienceType;
    }

    public void setAudienceType(AudienceType audienceType) {
        this.audienceType = audienceType;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    @Override
    public String toString() {
        return "Audience{" +
                "speaker=" + speaker +
                ", audienceType=" + audienceType +
                ", userKey='" + userKey + '\'' +
                ", id=" + id +
                '}';
    }
}
