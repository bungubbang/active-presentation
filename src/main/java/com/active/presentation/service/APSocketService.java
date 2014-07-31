package com.active.presentation.service;

import com.active.presentation.domain.Answer;
import com.active.presentation.domain.Audience;
import com.active.presentation.domain.PresentationDashboard;
import com.active.presentation.domain.Tag;
import com.active.presentation.domain.message.SocketResponseMessage;
import com.active.presentation.repository.AudienceRepository;
import com.active.presentation.repository.PresentationDashboardRepository;
import com.active.presentation.repository.TagRepository;
import com.active.presentation.repository.dto.AnswerResultDto;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/13/14
 */
@Service
public class APSocketService implements SocketService {

    @Autowired
    private AudienceRepository audienceRepository;

    @Autowired
    private PresentationDashboardRepository dashboardRepository;

    @Autowired
    private TagRepository tagRepository;

    public Audience generateAudience(String uid) {
        Audience audience = audienceRepository.findByUserKey(uid);
        if(audience == null) {
            audience = audienceRepository.save(new Audience(uid));
        }
        return audience;
    }

    public PresentationDashboard findOxDashBoard(Long id) {
        PresentationDashboard dashboard = dashboardRepository.findOne(id);
        if(dashboard == null) {
            throw new NullPointerException();
        }
        return dashboard;
    }

    @Override
    public SocketResponseMessage checkSecure(PresentationDashboard dashboard) {
        return new SocketResponseMessage(Lists.newArrayList(new AnswerResultDto(null, null, null, dashboard.getStatus())));
    }

    @Override
    public Set<Tag> parsingTags(String message) {
        String patternStr = "(?:\\s|\\A)[##]+([A-Za-z0-9-_ㄱ-ㅎㅏ-ㅣ가-힣]+)";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(message);

        Set<Tag> hashTags = new LinkedHashSet<Tag>();
        while (matcher.find()) {
            String hashTag = matcher.group().trim();
            if(!hashTag.isEmpty()) {
                String tagName = hashTag.replace("#", "");
                Tag tag = tagRepository.findByName(tagName);
                if(tag == null) {
                    tag = tagRepository.save(new Tag(tagName));
                }
                hashTags.add(tag);
            }
        }

        return hashTags;
    }
}
