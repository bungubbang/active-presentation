package com.active.presentation.repository;

import com.active.presentation.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 7/4/14
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    public Question findByAnswerList(String answerList);

    @Query("SELECT q FROM PresentationDashboard p JOIN p.questions q WHERE p.id = :id AND q.answerList = :answer")
    public Question searchBoardAndAnswer(@Param("id")Long dashboardId, @Param("answer") String answer);

    @Query("SELECT q FROM PresentationDashboard p JOIN p.questions q WHERE p.id = :boardId AND q.id = :id")
    public Question searchBoardAndId(@Param("boardId") Long dashboardId, @Param("id") Long id);
}
