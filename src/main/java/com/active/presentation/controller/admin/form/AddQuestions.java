package com.active.presentation.controller.admin.form;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 2014. 7. 22.
 */
public class AddQuestions {
    private Long boardId;
    private String question;

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "AddQuestions{" +
                "boardId=" + boardId +
                ", question='" + question + '\'' +
                '}';
    }
}
