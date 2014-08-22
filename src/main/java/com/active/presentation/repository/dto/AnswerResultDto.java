package com.active.presentation.repository.dto;

import java.util.Date;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/13/14
 */
public class AnswerResultDto {
    private long id;
    private String result;
    private long choice;
    private Date createdDate;
    private String title;
    private boolean status;
    private boolean anonymous;
    private String name;
    private String profileImage;

    public AnswerResultDto(long id, String result, long choice, Date createdDate, boolean status, boolean anonymous, String name, String profileImage) {
        this.id = id;
        this.result = result;
        this.choice = choice;
        this.createdDate = createdDate;
        this.status = status;
        this.anonymous = anonymous;
        this.name = name;
        this.profileImage = profileImage;
    }

    public AnswerResultDto(Long id, String result, Date createdDate, boolean status, boolean anonymous, String name, String profileImage) {
        this.id = id;
        this.result = result;
        this.createdDate = createdDate;
        this.status = status;
        this.anonymous = anonymous;
        this.name = name;
        this.profileImage = profileImage;
    }

    public AnswerResultDto(Long id, String result, String title, Date createdDate, boolean status, boolean anonymous, String name, String profileImage) {
        this.id = id;
        this.result = result;
        this.createdDate = createdDate;
        this.title = title;
        this.status = status;
        this.anonymous = anonymous;
        this.name = name;
        this.profileImage = profileImage;
    }

    public String getResult() {
        return result;
    }

    public long getChoice() {
        return choice;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public String getTitle() {
        return title;
    }

    public boolean isStatus() {
        return status;
    }

    public long getId() {
        return id;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public String getName() {
        return name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    @Override
    public String toString() {
        return "AnswerResultDto{" +
                "id=" + id +
                ", result='" + result + '\'' +
                ", choice=" + choice +
                ", createdDate=" + createdDate +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", anonymous=" + anonymous +
                ", name='" + name + '\'' +
                ", profileImage='" + profileImage + '\'' +
                '}';
    }
}
