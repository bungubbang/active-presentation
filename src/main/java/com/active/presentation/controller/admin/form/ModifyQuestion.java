package com.active.presentation.controller.admin.form;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 2014. 7. 22.
 */
public class ModifyQuestion {
    private Long id;
    private String question;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "ModifyQuestion{" +
                "id=" + id +
                ", question='" + question + '\'' +
                '}';
    }
}
