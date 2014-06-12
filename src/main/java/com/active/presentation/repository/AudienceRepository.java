package com.active.presentation.repository;

import com.active.presentation.domain.Audience;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/10/14
 */
public interface AudienceRepository extends JpaRepository<Audience, Long> {
    Audience findByUserKey(String userKey);
}
