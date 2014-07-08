package com.active.presentation.domain;

import javax.persistence.*;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/13/14
 */
@Entity
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    private String answerList;

    private Integer listOrder;

    public Question() {}

    public Question(String answerList) {
        this.answerList = answerList;
    }

    public Question(String answerList, Integer listOrder) {
        this.answerList = answerList;
        this.listOrder = listOrder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswerList() {
        return answerList;
    }

    public void setAnswerList(String answerList) {
        this.answerList = answerList;
    }

    public Integer getListOrder() {
        return listOrder;
    }

    public void setListOrder(Integer listOrder) {
        this.listOrder = listOrder;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", answerList='" + answerList + '\'' +
                ", listOrder=" + listOrder +
                '}';
    }
}
