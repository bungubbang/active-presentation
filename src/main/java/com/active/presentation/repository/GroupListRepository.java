package com.active.presentation.repository;

import com.active.presentation.domain.GroupLists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 2014. 8. 5.
 */
@Repository
public interface GroupListRepository extends JpaRepository<GroupLists, Long> {
    public List<GroupLists> findByGroupId(Long groupId);
    public GroupLists findByDashboardId(Long dashboardId);
    public GroupLists findByGroupIdAndListOrder(Long groupId, Integer order);
}
