package com.active.presentation.service;

import com.active.presentation.ApBootApplication;
import com.active.presentation.domain.Speaker;
import com.active.presentation.repository.SpeakerRepository;
import com.active.presentation.repository.dto.AdminHomeDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/30/14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {ApBootApplication.class})
@ActiveProfiles("local")
public class AdminDashboardServiceTest {

    @Autowired
    private AdminDashboardService adminDashboardService;

    @Autowired
    private SpeakerRepository speakerRepository;

    Speaker speaker;

    @Before
    public void setUp() {
        speaker = speakerRepository.findByEmail("bungubbang@nate.com");
    }

    @Test
    public void test() {
        // Given
        AdminHomeDto adminHome = adminDashboardService.getAdminHome(speaker);
        System.out.println("adminHome = " + adminHome);
        // When

        // Then

    }


}
