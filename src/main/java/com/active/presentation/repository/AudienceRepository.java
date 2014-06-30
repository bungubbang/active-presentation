package com.active.presentation.repository;

import com.active.presentation.domain.Audience;
import com.active.presentation.domain.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/10/14
 */
@Repository
public interface AudienceRepository extends JpaRepository<Audience, Long> {
    Audience findByUserKey(String userKey);

    @Query("SELECT u.userKey FROM Answer a join a.audience u join a.dashboard d join d.speaker s where s = :speaker GROUP BY u")
    List<String> findBySpeaker(@Param("speaker") Speaker speaker);

    @Query("SELECT u.userKey FROM Answer a join a.audience u join a.dashboard d join d.speaker s " +
            "WHERE s = :speaker AND a.modifyDate < :date GROUP BY u")
    List<String> findBySpeakerBeforeDate(@Param("speaker") Speaker speaker, @Param("date") Date date);

}
