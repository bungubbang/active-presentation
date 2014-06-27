package com.active.presentation.repository;

import com.active.presentation.domain.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/10/14
 */
@Repository
public interface SpeakerRepository extends JpaRepository<Speaker, Long> {
    Speaker findByEmail(String email);

}
