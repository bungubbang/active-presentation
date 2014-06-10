package com.active.presentation.core.repository;

import com.active.presentation.core.domain.Audience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/10/14
 */
public interface AudienceRepository extends JpaRepository<Audience, Long> {
    Audience findByUserKey(String userKey);
}
