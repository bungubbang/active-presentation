package com.active.presentation.ox.repository;

import com.active.presentation.ox.domain.OxDashBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/10/14
 */
public interface OxDashBoardRepository extends JpaRepository<OxDashBoard, Long> {
}
