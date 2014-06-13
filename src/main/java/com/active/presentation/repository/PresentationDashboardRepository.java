package com.active.presentation.repository;

import com.active.presentation.domain.PresentationDashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/11/14
 */
public interface PresentationDashboardRepository extends JpaRepository<PresentationDashboard, Long>, JpaSpecificationExecutor<PresentationDashboard> {
}