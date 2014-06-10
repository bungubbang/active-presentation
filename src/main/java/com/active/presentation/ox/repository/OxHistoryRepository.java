package com.active.presentation.ox.repository;

import com.active.presentation.core.domain.Audience;
import com.active.presentation.ox.domain.OxDashBoard;
import com.active.presentation.ox.domain.OxHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/10/14
 */
public interface OxHistoryRepository extends JpaRepository<OxHistory, Long> {
    OxHistory findByOxDashBoardAndAudience(OxDashBoard board, Audience audience);
    List<OxHistory> findByOxDashBoard(OxDashBoard board);
    long countByOxDashBoardAndResult(OxDashBoard board, String result);
}
