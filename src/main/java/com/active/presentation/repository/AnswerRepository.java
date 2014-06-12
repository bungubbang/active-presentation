package com.active.presentation.repository;

import com.active.presentation.domain.Answer;
import com.active.presentation.domain.Audience;
import com.active.presentation.domain.PresentationDashboard;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/11/14
 */
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    long countByDashboardAndResult(PresentationDashboard dashboard, String result);
    Answer findByDashboardAndAudience(PresentationDashboard dashboard, Audience audience);
}
