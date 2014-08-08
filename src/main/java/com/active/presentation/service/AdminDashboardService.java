package com.active.presentation.service;

import com.active.presentation.controller.admin.form.BoardModifyForm;
import com.active.presentation.controller.admin.form.GroupForm;
import com.active.presentation.domain.*;
import com.active.presentation.repository.*;
import com.active.presentation.repository.dto.AdminHomeDto;
import com.active.presentation.security.SecurityContext;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

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
    private TagRepository tagRepository;

    @Autowired
    private DashboardGroupRepository groupRepository;

    @Autowired
    private GroupListRepository groupListRepository;

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
    }

    @Override
    public Set<Tag> addTags(String name, PresentationDashboard dashboard, Long questionId) {
        Set<Tag> tags = dashboard.getTags();
        if(tags == null) {
            tags = new HashSet<Tag>();
        }
        Tag tag = tagRepository.findByName(name);
        if(tag == null) {
            tag = tagRepository.save(new Tag(name));
        }
        tags.add(tag);
        return tags;
    }

    @Override
    public DashboardGroup addGroup(GroupForm form) {
        DashboardGroup group = groupRepository.save(new DashboardGroup(SecurityContext.getCurrentUser(), form.getTitle()));
        String[] boardLists = form.getBoardList().split(",");
        for (int i = 0; i < boardLists.length; i++) {
            GroupLists groupLists = new GroupLists();
            groupLists.setDashboardId(Long.valueOf(boardLists[i]));
            groupLists.setGroupId(group.getId());
            groupLists.setListOrder(i + 1);
            groupLists.setTotal(boardLists.length);
            groupListRepository.save(groupLists);
        }

        return group;
    }

    @Override
    public DashboardGroup modifyGroup(GroupForm form) {
        DashboardGroup group = groupRepository.findOne(form.getId());
        group.setTitle(form.getTitle());
        group.setModifyDate(new Date());
        groupRepository.save(group);

        List<GroupLists> byGroupId = groupListRepository.findByGroupId(group.getId());
        for (GroupLists groupLists : byGroupId) {
            groupListRepository.delete(groupLists);
        }

        String[] boardLists = form.getBoardList().split(",");
        for (int i = 0; i < boardLists.length; i++) {
            GroupLists groupLists = new GroupLists();
            groupLists.setDashboardId(Long.valueOf(boardLists[i]));
            groupLists.setGroupId(group.getId());
            groupLists.setListOrder(i + 1);
            groupLists.setTotal(boardLists.length);
            groupListRepository.save(groupLists);
        }

        return group;
    }

    @Override
    public List<PresentationDashboard> selectedBoards(Long groupId) {
        List<PresentationDashboard> dashboards = new ArrayList<PresentationDashboard>();
        List<GroupLists> groupLists = groupListRepository.findByGroupId(groupId);
        for (GroupLists groupList : groupLists) {
            dashboards.add(dashboardRepository.findOne(groupList.getDashboardId()));
        }
        return dashboards;
    }

    @Override
    public List<PresentationDashboard> notSelectedBoards() {
        Speaker speaker = SecurityContext.getCurrentUser();
        List<PresentationDashboard> dashboards = dashboardRepository.findBySpeaker(speaker);
        List<DashboardGroup> groups = groupRepository.findBySpeaker(speaker);
        for (DashboardGroup group : groups) {
            List<GroupLists> groupList = groupListRepository.findByGroupId(group.getId());
            for (GroupLists groupLists : groupList) {
                PresentationDashboard dashboard = dashboardRepository.findOne(groupLists.getDashboardId());
                dashboards.remove(dashboard);
            }
        }

        return dashboards;
    }

    @Override
    public void deleteGroup(Long id) {
        groupRepository.delete(id);
        List<GroupLists> byGroupId = groupListRepository.findByGroupId(id);
        for (GroupLists groupLists : byGroupId) {
            groupListRepository.delete(groupLists);
        }
    }

    private Long convertNullToZero(Long count) {
        return (count == null)? 0L: count;
    }
}
