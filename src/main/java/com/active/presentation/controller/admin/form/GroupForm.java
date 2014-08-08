package com.active.presentation.controller.admin.form;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 2014. 8. 5.
 */
public class GroupForm {
    private Long id;
    private String title;
    private String boardList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBoardList() {
        return boardList;
    }

    public void setBoardList(String boardList) {
        this.boardList = boardList;
    }

    @Override
    public String toString() {
        return "GroupForm{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", boardList='" + boardList + '\'' +
                '}';
    }
}
