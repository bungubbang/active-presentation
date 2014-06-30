package com.active.presentation.repository;

import com.active.presentation.ApBootApplication;
import com.active.presentation.domain.Speaker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/30/14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {ApBootApplication.class})
@ActiveProfiles("local")
public class AudienceRepositoryTest {

    @Autowired
    private AudienceRepository audienceRepository;

    @Autowired
    private SpeakerRepository speakerRepository;

    Speaker speaker;

    @Before
    public void setUp() {
        speaker = speakerRepository.findByEmail("bungubbang@nate.com");
    }

    @Test
    public void countAudienceBySpeaker() {
        // Given
        System.out.println("speaker = " + speaker);

        List<String> strings = audienceRepository.findBySpeaker(speaker);
        for (String string : strings) {
            System.out.println("string = " + string);
        }
        // When

        // Then

    }
}
