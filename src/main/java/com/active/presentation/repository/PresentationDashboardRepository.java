package com.active.presentation.repository;

import com.active.presentation.domain.PresentationDashboard;
import com.active.presentation.domain.PresentationType;
import com.active.presentation.domain.Speaker;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/11/14
 */
@Repository
public interface PresentationDashboardRepository extends JpaRepository<PresentationDashboard, Long>, JpaSpecificationExecutor<PresentationDashboard> {
    Long countBySpeaker(Speaker speaker);
    Long countBySpeakerAndCreatedDateBefore(Speaker speaker, Date createdDate);

    Long countBySpeakerAndPresentationType(Speaker speaker, PresentationType presentationType);
    Long countBySpeakerAndPresentationTypeAndCreatedDateBefore(Speaker speaker, PresentationType presentationType, Date createdDate);

    List<PresentationDashboard> findBySpeaker(Speaker speaker, Pageable pageable);
}
