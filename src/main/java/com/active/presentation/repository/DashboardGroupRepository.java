package com.active.presentation.repository;

import com.active.presentation.domain.DashboardGroup;
import com.active.presentation.domain.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 2014. 8. 5.
 */
@Repository
public interface DashboardGroupRepository extends JpaRepository<DashboardGroup, Long> {
    public List<DashboardGroup> findBySpeaker(Speaker speaker);
}
