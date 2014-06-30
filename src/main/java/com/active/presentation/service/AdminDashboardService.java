package com.active.presentation.service;

import com.active.presentation.domain.PresentationDashboard;
import com.active.presentation.domain.PresentationType;
import com.active.presentation.domain.Speaker;
import com.active.presentation.repository.AnswerRepository;
import com.active.presentation.repository.AudienceRepository;
import com.active.presentation.repository.PresentationDashboardRepository;
import com.active.presentation.repository.dto.AdminHomeDto;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/27/14
 */
@Service
public class AdminDashboardService implements AdminService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private PresentationDashboardRepository dashboardRepository;

    @Autowired
    private AudienceRepository audienceRepository;

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

        Long answerTotal = answerRepository.countBySpeaker(speaker);
        if(answerTotal == null) {
            answerTotal = 0L;
        }
        adminHomeDto.setAnswerTotal(answerTotal);
        adminHomeDto.setAnswerTotalDiff(answerTotal - answerRepository.countBySpeakerBeforeDate(speaker, new DateTime().minusWeeks(1).toDate()));
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
}
