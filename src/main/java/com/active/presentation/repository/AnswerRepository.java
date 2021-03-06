package com.active.presentation.repository;

import com.active.presentation.domain.*;
import com.active.presentation.domain.message.AnswerAudienceListMessage;
import com.active.presentation.repository.dto.AnswerResultDto;
import com.active.presentation.repository.dto.AnswerTransactionDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/11/14
 */
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> , JpaSpecificationExecutor<Answer> {

    Long countByDashboardAndResult(PresentationDashboard dashboard, String result);

    List<Answer> findByResultId(Long resultId);

    @Query("SELECT new com.active.presentation.domain.message.AnswerAudienceListMessage(a.modifyDate as createdDate, a.name as name, a.profileImage as profileImage, a.result as result, a.resultId as resultId) FROM Answer a WHERE a.dashboard = :dashboard AND a.anonymous = :anonymous ORDER BY a.modifyDate desc")
    List<AnswerAudienceListMessage> findByAnswerAudienceList(@Param("dashboard") PresentationDashboard dashboard, @Param("anonymous") Boolean anonymous);

    @Query("SELECT count(*) FROM Answer a join a.dashboard d join d.speaker s where s = :speaker GROUP BY s")
    Long countBySpeaker(@Param("speaker") Speaker speaker);

    @Query("SELECT count(*) FROM Answer a join a.dashboard d join d.speaker s " +
            "WHERE s = :speaker AND a.modifyDate < :date GROUP BY s")
    Long countBySpeakerBeforeDate(@Param("speaker") Speaker speaker, @Param("date") Date date);

    @Query("SELECT count(*) FROM Answer a join a.dashboard d join d.speaker s " +
            "WHERE s = :speaker AND a.modifyDate > :date GROUP BY s")
    Long countBySpeakerAfterDate(@Param("speaker") Speaker speaker, @Param("date") Date date);

    Answer findByDashboardAndAudience(PresentationDashboard dashboard, Audience audience);

    @Query("SELECT new com.active.presentation.repository.dto.AnswerResultDto(a.resultId as id, q.answerList as result, count(a.result) as choice, a.modifyDate as createdDate, d.status as status, a.anonymous as anonymous, a.name as name, a.profileImage as profileImage) FROM Answer a JOIN a.dashboard d JOIN d.questions q WHERE a.dashboard = :dashboard AND q.id = a.resultId GROUP BY q.answerList ORDER BY count(q.answerList) desc ")
    List<AnswerResultDto> resultByDashboard(@Param("dashboard") PresentationDashboard dashboard);

    @Query("SELECT new com.active.presentation.repository.dto.AnswerResultDto(a.resultId as id, a.result as result, a.modifyDate as createdDate, d.status as status, a.anonymous as anonymous, a.name as name, a.profileImage as profileImage) FROM Answer a JOIN a.dashboard d WHERE a.dashboard = :dashboard")
    List<AnswerResultDto> findByDashboardOnAnswerResultDto(@Param("dashboard") PresentationDashboard dashboard);

    @Query("SELECT new com.active.presentation.repository.dto.AnswerResultDto(a.resultId as id, a.result as result, a.modifyDate as createdDate, d.status as status, a.anonymous as anonymous, a.name as name, a.profileImage as profileImage) FROM Answer a JOIN a.dashboard d WHERE a.dashboard = :dashboard AND a.result LIKE :tags")
    List<AnswerResultDto> findByDashboardTagsOnAnswerResultDto(@Param("dashboard") PresentationDashboard dashboard, @Param("tags") String tags);

    @Query("SELECT new com.active.presentation.repository.dto.AnswerResultDto(d.id as id, a.result as result, d.title as title, a.modifyDate as createdDate, d.status as status, a.anonymous as anonymous, a.name as name, a.profileImage as profileImage) FROM Answer a join a.dashboard d join d.speaker s " +
            "WHERE s = :speaker AND d.presentationType = :dashboardType order by a.createdDate desc")
    List<AnswerResultDto> findByRecentBySpeakerAndType(@Param("speaker") Speaker speaker, @Param("dashboardType") PresentationType presentationType, Pageable pageable);

    @Query("select new com.active.presentation.repository.dto.AnswerTransactionDto(count(a) as count, year(a.createdDate) ||'-'|| month(a.createdDate) ||'-'|| day(a.createdDate) as date ) " +
            " FROM Answer a join a.dashboard d join d.speaker s where s = :speaker AND d.createdDate >= :date" +
            " group by year(a.createdDate) ||'-'|| month(a.createdDate) ||'-'|| day(a.createdDate)" +
            " order by a.createdDate asc")
    List<AnswerTransactionDto> countAnswerAfterDate(@Param("speaker") Speaker speaker, @Param("date") Date date);

    @Query("select new com.active.presentation.repository.dto.AnswerResultDto(a.resultId as id, a.result as result, a.modifyDate as createdDate, d.status as status, a.anonymous as anonymous, a.name as name, a.profileImage as profileImage) FROM Answer a join a.tags t JOIN a.dashboard d WHERE t.name = :name")
    List<AnswerResultDto> findByTagsName(@Param("name") String name);

    @Query("UPDATE Answer a SET a.result = :result WHERE a.resultId = :id")
    void modifyAnswerResult(@Param("id") Long id, @Param("result") String result);
}
