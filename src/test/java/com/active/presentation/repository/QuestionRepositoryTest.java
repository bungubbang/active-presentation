package com.active.presentation.repository;

import com.active.presentation.ApBootApplication;
import com.active.presentation.domain.PresentationDashboard;
import com.active.presentation.domain.Question;
import com.active.presentation.domain.Speaker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {ApBootApplication.class})
@ActiveProfiles("local")
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private PresentationDashboardRepository dashboardRepository;

    @Autowired
    private SpeakerRepository speakerRepository;

    @Test
    public void test() {
        // Given

        Question red = questionRepository.searchBoardAndAnswer((long) 4, "red");
        System.out.println("red = " + red);
        // When

        // Then

    }

}