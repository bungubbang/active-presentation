package com.active.presentation.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

    public Audience() {}

    public Audience(String userKey) {
        this.userKey = userKey;
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

    @Override
    public String toString() {
        return "Audience{" +
                "id=" + id +
                ", userKey='" + userKey + '\'' +
                '}';
    }
}
