package com.active.presentation.repository;

import com.active.presentation.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 2014. 7. 25.
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    public Tag findByName(String name);
}
