package com.active.presentation.repository;

import com.active.presentation.ApBootApplication;
import com.active.presentation.PersistenceConfiguration;
import com.active.presentation.WebSocketConfig;
import com.active.presentation.domain.Answer;
import com.active.presentation.domain.PresentationDashboard;
import com.active.presentation.repository.dto.AnswerResultDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/13/14
 */
@Profile("local")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {ApBootApplication.class})
public class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private PresentationDashboardRepository dashboardRepository;

    @Test
    public void testResultByDashboard() throws Exception {
        PresentationDashboard dashboard = dashboardRepository.findOne(Long.valueOf(1));

        System.out.println("dashboard = " + dashboard);
//        for (Answer answer : all) {
//            System.out.println("answer = " + answer);
//        }
//        List<Map<String,Long>> items = answerRepository.resultByDashboard(dashboard);
        List<AnswerResultDto> answerResultDtos = answerRepository.resultByDashboard(dashboard);
        for (AnswerResultDto answerResultDto : answerResultDtos) {
            System.out.println("answerResultDto = " + answerResultDto);
        }


    }

}
