package com.active.presentation.repository.dto;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/13/14
 */
public class AnswerResultDto {
    private String result;
    private long choice;

    public AnswerResultDto(String result, long choice) {
        this.result = result;
        this.choice = choice;
    }


    public String getResult() {
        return result;
    }

    public long getChoice() {
        return choice;
    }


    @Override
    public String toString() {
        return "AnswerResultDto{" +
                "result='" + result + '\'' +
                ", choice=" + choice +
                '}';
    }
}
