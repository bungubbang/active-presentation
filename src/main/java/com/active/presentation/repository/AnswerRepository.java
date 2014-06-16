package com.active.presentation.repository;

import com.active.presentation.domain.Answer;
import com.active.presentation.domain.Audience;
import com.active.presentation.domain.PresentationDashboard;
import com.active.presentation.repository.dto.AnswerResultDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/11/14
 */
public interface AnswerRepository extends JpaRepository<Answer, Long> , JpaSpecificationExecutor<Answer> {
    long countByDashboardAndResult(PresentationDashboard dashboard, String result);
    Answer findByDashboardAndAudience(PresentationDashboard dashboard, Audience audience);

    @Query("SELECT new com.active.presentation.repository.dto.AnswerResultDto(a.result as result, count(a.result) as choice) FROM Answer a WHERE a.dashboard = :dashboard GROUP BY a.result")
    List<AnswerResultDto> resultByDashboard(@Param("dashboard") PresentationDashboard dashboard);

    @Query("SELECT new com.active.presentation.repository.dto.AnswerResultDto(a.result as result) FROM Answer a WHERE a.dashboard = :dashboard")
    List<AnswerResultDto> findByDashboardOnAnswerResultDto(@Param("dashboard") PresentationDashboard dashboard);
}
