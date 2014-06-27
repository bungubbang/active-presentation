package com.active.presentation.repository;

import com.active.presentation.domain.*;
import com.active.presentation.repository.dto.AnswerResultDto;
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
public interface AnswerRepository extends JpaRepository<Answer, Long> , JpaSpecificationExecutor<Answer> {

    Long countByDashboardAndResult(PresentationDashboard dashboard, String result);

    @Query("SELECT count(*) FROM Answer a join a.dashboard d join d.speaker s where s = :speaker GROUP BY s")
    Long countBySpeaker(@Param("speaker") Speaker speaker);

    @Query("SELECT count(*) FROM Answer a join a.dashboard d join d.speaker s " +
            "WHERE s = :speaker AND a.modifyDate < :date GROUP BY s")
    Long countBySpeakerBeforeDate(@Param("speaker") Speaker speaker, @Param("date") Date date);

    @Query("SELECT count(*) FROM Answer a join a.dashboard d join d.speaker s " +
            "WHERE s = :speaker AND a.modifyDate > :date GROUP BY s")
    Long countBySpeakerAfterDate(@Param("speaker") Speaker speaker, @Param("date") Date date);

    Answer findByDashboardAndAudience(PresentationDashboard dashboard, Audience audience);

    @Query("SELECT new com.active.presentation.repository.dto.AnswerResultDto(a.result as result, count(a.result) as choice) FROM Answer a WHERE a.dashboard = :dashboard GROUP BY a.result")
    List<AnswerResultDto> resultByDashboard(@Param("dashboard") PresentationDashboard dashboard);

    @Query("SELECT new com.active.presentation.repository.dto.AnswerResultDto(a.result as result) FROM Answer a WHERE a.dashboard = :dashboard")
    List<AnswerResultDto> findByDashboardOnAnswerResultDto(@Param("dashboard") PresentationDashboard dashboard);

    @Query("SELECT new com.active.presentation.repository.dto.AnswerResultDto(a.result as result) FROM Answer a join a.dashboard d join d.speaker s " +
            "WHERE s = :speaker AND d.presentationType = :dashboardType")
    List<AnswerResultDto> findByRecentBySpeakerAndType(@Param("speaker") Speaker speaker, @Param("dashboardType") PresentationType presentationType);
}
