package com.active.presentation.domain.message;

import com.active.presentation.repository.dto.AnswerResultDto;

import java.util.List;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/11/14
 */
public class SocketResponseMessage {
    private List<AnswerResultDto> result;
    private String lastAnswer;

    public SocketResponseMessage(List<AnswerResultDto> result) {
        this.result = result;
    }

    public SocketResponseMessage(List<AnswerResultDto> result, String lastAnswer) {
        this.result = result;
        this.lastAnswer = lastAnswer;
    }

    public List<AnswerResultDto> getResult() {
        return result;
    }

    public String getLastAnswer() {
        return lastAnswer;
    }
}