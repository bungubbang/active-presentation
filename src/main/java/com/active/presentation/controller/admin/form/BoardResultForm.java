package com.active.presentation.controller.admin.form;

import com.active.presentation.repository.dto.AnswerResultDto;

import java.util.List;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 7/7/14
 */
public class BoardResultForm {
    private Long dashboardId;
    private List<AnswerResultDto> results;

    public Long getDashboardId() {
        return dashboardId;
    }

    public void setDashboardId(Long dashboardId) {
        this.dashboardId = dashboardId;
    }

    public List<AnswerResultDto> getResults() {
        return results;
    }

    public void setResults(List<AnswerResultDto> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "BoardResultForm{" +
                "dashboardId=" + dashboardId +
                ", results=" + results +
                '}';
    }
}
