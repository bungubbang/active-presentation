package com.active.presentation.repository;

import com.active.presentation.domain.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/10/14
 */
public interface SpeakerRepository extends JpaRepository<Speaker, Long> {
}
