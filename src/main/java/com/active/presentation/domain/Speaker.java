package com.active.presentation.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/10/14
 */
@Entity
public class Speaker {

    @Id @GeneratedValue
    private Long id;

    private String email;

    private String name;
    private String profileImage;

    private String platform;
    private String providerId;

    public Speaker() {}

    public Speaker(String email) {
        this.email = email;
    }

    public Speaker(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Speaker(String name, String email, String profileImage, String platform, String providerId) {
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
        this.platform = platform;
        this.providerId = providerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    @Override
    public String toString() {
        return "Speaker{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", platform='" + platform + '\'' +
                ", providerId='" + providerId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Speaker)) return false;

        Speaker speaker = (Speaker) o;

        if (id != null ? !id.equals(speaker.id) : speaker.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
