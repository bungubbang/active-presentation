package com.active.presentation.repository;

import com.active.presentation.ApBootApplication;
import com.active.presentation.domain.Answer;
import com.active.presentation.domain.PresentationDashboard;
import com.active.presentation.domain.Speaker;
import com.active.presentation.repository.dto.AnswerResultDto;
import com.active.presentation.repository.dto.AnswerTransactionDto;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/13/14
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {ApBootApplication.class})
@ActiveProfiles("local")
public class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private PresentationDashboardRepository dashboardRepository;

    @Autowired
    private SpeakerRepository speakerRepository;

    @Test
    public void testFindBySpeaker() {
        // Given
        Speaker speaker = speakerRepository.findByEmail("bungubbang@nate.com");
        System.out.println("speaker = " + speaker);
        // When
        List<AnswerTransactionDto> objects = answerRepository.countAnswerAfterDate(speaker, new DateTime().minusWeeks(1).toDate());
        for (AnswerTransactionDto object : objects) {
            System.out.println("object = " + object);
        }
    }

    @Test
    public void test() {
        // Given
        Date date = new DateTime("2014-07-25").toDate();
        System.out.println("date = " + date);
        // When

        // Then

    }

}
