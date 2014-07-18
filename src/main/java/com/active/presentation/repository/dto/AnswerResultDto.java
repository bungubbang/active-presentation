package com.active.presentation.repository.dto;

import java.util.Date;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/13/14
 */
public class AnswerResultDto {
    private String result;
    private long choice;
    private Date createdDate;
    private String title;
    private boolean status;

    public AnswerResultDto(String result, long choice, Date createdDate, boolean status) {
        this.result = result;
        this.choice = choice;
        this.createdDate = createdDate;
        this.status = status;
    }

    public AnswerResultDto(String result, Date createdDate, boolean status) {
        this.result = result;
        this.createdDate = createdDate;
        this.status = status;
    }

    public AnswerResultDto(String result, String title, Date createdDate, boolean status) {
        this.result = result;
        this.createdDate = createdDate;
        this.title = title;
        this.status = status;
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

    @Override
    public String toString() {
        return "AnswerResultDto{" +
                "result='" + result + '\'' +
                ", choice=" + choice +
                ", createdDate=" + createdDate +
                ", title='" + title + '\'' +
                ", status=" + status +
                '}';
    }
}
