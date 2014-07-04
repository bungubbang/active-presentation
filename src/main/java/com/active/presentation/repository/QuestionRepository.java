package com.active.presentation.repository;

import com.active.presentation.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 7/4/14
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
