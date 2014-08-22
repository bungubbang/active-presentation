package com.active.presentation.domain.message;

import java.util.Date;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 2014. 8. 21.
 */
public class AnswerAudienceListMessage {
    private Date createdDate;
    private String name;
    private String profileImage;
    private String result;
    private Long resultId;

    public AnswerAudienceListMessage(Date createdDate, String name, String profileImage, String result, Long resultId) {
        this.createdDate = createdDate;
        this.name = name;
        this.profileImage = profileImage;
        this.result = result;
        this.resultId = resultId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    @Override
    public String toString() {
        return "AnswerAudienceListMessage{" +
                "createdDate=" + createdDate +
                ", name='" + name + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", result='" + result + '\'' +
                ", resultId=" + resultId +
                '}';
    }
}
