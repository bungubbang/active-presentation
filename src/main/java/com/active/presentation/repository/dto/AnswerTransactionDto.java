package com.active.presentation.repository.dto;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/30/14
 */
public class AnswerTransactionDto {
    private Long count;
    private Date date;

    public AnswerTransactionDto(Long count, String date) {
        this.count = count;
        this.date = new DateTime(date).toDate();
    }

    public Long getCount() {
        return count;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "AnswerTransactionDto{" +
                "count=" + count +
                ", date='" + date + '\'' +
                '}';
    }
}
