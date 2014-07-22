package com.active.presentation.repository;

import com.active.presentation.domain.PresentationDashboard;
import com.active.presentation.domain.PresentationType;
import com.active.presentation.domain.Speaker;
import com.active.presentation.repository.dto.DashboardTop10Dto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    List<PresentationDashboard> findBySpeaker(Speaker speaker);
    List<PresentationDashboard> findBySpeaker(Speaker speaker, Pageable pageable);
    List<PresentationDashboard> findBySpeakerAndPresentationType(Speaker speaker, PresentationType presentationType, Sort sort);

    @Query("SELECT new com.active.presentation.repository.dto.DashboardTop10Dto(d.id as id, d.title as label, count(d) as data) FROM Answer a join a.dashboard d join d.speaker s where s = :speaker GROUP BY d order by count(d) desc")
    List<DashboardTop10Dto> getTop10(@Param("speaker") Speaker speaker, Pageable pageable);
}
