package com.active.presentation.repository;

import com.active.presentation.ApBootApplication;
import com.active.presentation.domain.Answer;
import com.active.presentation.domain.PresentationDashboard;
import com.active.presentation.domain.PresentationType;
import com.active.presentation.domain.Speaker;
import com.active.presentation.repository.dto.AnswerResultDto;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/27/14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {ApBootApplication.class})
@ActiveProfiles("local")
public class PresentationDashboardRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private PresentationDashboardRepository dashboardRepository;

    @Autowired
    private SpeakerRepository speakerRepository;

    @Test
    public void testCountBySpeaker() throws Exception {
        Speaker speaker = speakerRepository.findByEmail("bungubbang@nate.com");
        System.out.println("speaker = " + speaker);
        Long aLong = dashboardRepository.countBySpeakerAndPresentationType(speaker, PresentationType.QNA);
        System.out.println("aLong = " + aLong);
    }
}
