package com.active.presentation.controller.admin.form;

import com.sun.istack.internal.NotNull;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 7/4/14
 */
public class BoardStatusForm {

    @NotNull
    private Long id;

    @NotNull
    private Boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BoardStatusForm{" +
                "id=" + id +
                ", status=" + status +
                '}';
    }
}
