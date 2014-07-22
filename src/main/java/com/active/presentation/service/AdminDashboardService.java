package com.active.presentation.service;

import com.active.presentation.controller.admin.form.BoardModifyForm;
import com.active.presentation.domain.PresentationDashboard;
import com.active.presentation.domain.PresentationType;
import com.active.presentation.domain.Question;
import com.active.presentation.domain.Speaker;
import com.active.presentation.repository.*;
import com.active.presentation.repository.dto.AdminHomeDto;
import com.active.presentation.security.SecurityContext;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/27/14
 */
@Service
public class AdminDashboardService implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminDashboardService.class);

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private PresentationDashboardRepository dashboardRepository;

    @Autowired
    private AudienceRepository audienceRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SpeakerRepository speakerRepository;

    @Autowired
    private Environment env;

    @Override
    public AdminHomeDto getAdminHome(Speaker speaker) {
        AdminHomeDto adminHomeDto = new AdminHomeDto();

        Long pollTotal = dashboardRepository.countBySpeaker(speaker);
        adminHomeDto.setPollTotal(pollTotal);
        adminHomeDto.setPollTotalDiff(pollTotal - dashboardRepository.countBySpeakerAndCreatedDateBefore(speaker, new DateTime().minusWeeks(1).toDate()));

        Long oxTotal = dashboardRepository.countBySpeakerAndPresentationType(speaker, PresentationType.OX);
        adminHomeDto.setOxTotal(oxTotal);
        adminHomeDto.setOxTotalDiff(oxTotal - dashboardRepository.countBySpeakerAndPresentationTypeAndCreatedDateBefore(speaker, PresentationType.OX, new DateTime().minusWeeks(1).toDate()));

        Long mcTotal = dashboardRepository.countBySpeakerAndPresentationType(speaker, PresentationType.MULTIPLE_CHOICE);
        adminHomeDto.setMultipleTotal(mcTotal);
        adminHomeDto.setMultipleTotalDiff(mcTotal - dashboardRepository.countBySpeakerAndPresentationTypeAndCreatedDateBefore(speaker, PresentationType.MULTIPLE_CHOICE, new DateTime().minusWeeks(1).toDate()));

        Long qnaTotal = dashboardRepository.countBySpeakerAndPresentationType(speaker, PresentationType.QNA);
        adminHomeDto.setQnaTotal(qnaTotal);
        adminHomeDto.setQnaTotalDiff(qnaTotal - dashboardRepository.countBySpeakerAndPresentationTypeAndCreatedDateBefore(speaker, PresentationType.QNA, new DateTime().minusWeeks(1).toDate()));
        adminHomeDto.setQna(answerRepository.findByRecentBySpeakerAndType(speaker, PresentationType.QNA, new PageRequest(0, 5)));

        Long answerTotal = convertNullToZero(answerRepository.countBySpeaker(speaker));
        Long answerTotalDiff = convertNullToZero(answerRepository.countBySpeakerBeforeDate(speaker, new DateTime().minusWeeks(1).toDate()));

        adminHomeDto.setAnswerTotal(answerTotal);
        adminHomeDto.setAnswerTotalDiff(answerTotal - answerTotalDiff);
        adminHomeDto.setAnswerToday(answerRepository.countBySpeakerAfterDate(speaker, new DateTime().withTimeAtStartOfDay().toDate()));

        /**
         *  count 숫자로 바로 불러오는 방식을 생각해봐야함.
         */
        Long audienceTotal = (long) audienceRepository.findBySpeaker(speaker).size();
        adminHomeDto.setAudienceTotal(audienceTotal);
        adminHomeDto.setAudienceTotalDiff(audienceTotal - audienceRepository.findBySpeakerBeforeDate(speaker, new DateTime().minusWeeks(1).toDate()).size());

        adminHomeDto.setLatest(dashboardRepository.findBySpeaker(speaker, new PageRequest(0, 5)));
        adminHomeDto.setTop10(dashboardRepository.getTop10(speaker, new PageRequest(0, 10)));

        adminHomeDto.setTransaction(answerRepository.countAnswerAfterDate(speaker, new DateTime().minusMonths(1).toDate()));

        return adminHomeDto;
    }

    @Override
    public PresentationDashboard addBoard(PresentationDashboard dashboard, String questionList) {
        dashboard.setSpeaker(SecurityContext.getCurrentUser());
        List<Question> questions = new ArrayList<Question>();
        if(dashboard.getPresentationType().equals(PresentationType.OX)) {
            questions.add(questionRepository.save(new Question("O")));
            questions.add(questionRepository.save(new Question("X")));
        } else if(dashboard.getPresentationType().equals(PresentationType.MULTIPLE_CHOICE)) {
            String[] qList = questionList.split(",");
            for (int i = 0; i < qList.length; i++) {
                questions.add(questionRepository.save(new Question(qList[i], i + 1)));
            }
            dashboard.setChoiceCount(questions.size());
        } else if(dashboard.getPresentationType().equals(PresentationType.QNA)) {
            questions.add(questionRepository.save(new Question("Q")));
        }
        dashboard.setQuestions(questions);
        return dashboardRepository.save(dashboard);
    }

    @Override
    public PresentationDashboard modifyBoard(BoardModifyForm boardModifyForm) {
        PresentationDashboard dashboard = dashboardRepository.findOne(boardModifyForm.getId());
        if(dashboard == null || !dashboard.getSpeaker().equals(SecurityContext.getCurrentUser())) {
            return dashboard;
        }
        dashboard.setTitle(boardModifyForm.getTitle());
        dashboard.setSecure(boardModifyForm.isSecure());
        dashboard.setStatus(boardModifyForm.isStatus());
        dashboard.setAnonymous(boardModifyForm.isAnonymous());

        if(dashboard.getPresentationType().equals(PresentationType.MULTIPLE_CHOICE)) {
            List<Question> questions = new ArrayList<Question>();
            String[] qList = boardModifyForm.getQuestionList().split(",");
            for (int i = 0; i < qList.length; i++) {
                Question question = questionRepository.searchBoardAndId(dashboard.getId(), Long.valueOf(qList[i]));
                question.setListOrder(i + 1);
                questions.add(questionRepository.save(question));
            }
            dashboard.setQuestions(questions);
            dashboard.setChoiceCount(questions.size());
        }

        return dashboardRepository.save(dashboard);
    }

    @Override
    public void deleteUser(Long id) {
        Speaker speaker = speakerRepository.findOne(id);
        speaker.setStatus(false);
        speakerRepository.save(speaker);
//        List<PresentationDashboard> dashboards = dashboardRepository.findBySpeaker(speaker);
//        for (PresentationDashboard dashboard : dashboards) {
//            dashboardRepository.delete(dashboard);
//        }
//        speakerRepository.delete(speaker);
//
//        Connection con = null;
//        PreparedStatement statement = null;
//        try {
//            con = DriverManager.getConnection(env.getProperty("jdbc.url"), env.getProperty("jdbc.username"), env.getProperty("jdbc.password"));
//
//            statement = con.prepareStatement("DELETE FROM ap.UserConnection WHERE userId=?");
//            statement.setLong(1, id);
//
//            statement.executeUpdate();
//
//        } catch (SQLException sqex) {
//            logger.error("SQLException: " + sqex.getMessage());
//            logger.error("SQLState: " + sqex.getSQLState());
//        } finally {
//            if (statement != null) {
//                try {
//                    statement.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
    }

    private Long convertNullToZero(Long count) {
        return (count == null)? 0L: count;
    }
}
