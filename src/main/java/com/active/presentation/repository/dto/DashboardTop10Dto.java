package com.active.presentation.repository.dto;

import com.active.presentation.domain.PresentationDashboard;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/30/14
 */
public class DashboardTop10Dto {
    private Long id;
    private String label;
    private Long data;

    public DashboardTop10Dto(Long id, String label, Long data) {
        this.id = id;
        this.label = label;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public Long getData() {
        return data;
    }

    @Override
    public String toString() {
        return "DashboardTop10Dto{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", data=" + data +
                '}';
    }
}
