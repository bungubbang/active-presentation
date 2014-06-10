package com.active.presentation.ox.controller;

import com.active.presentation.core.domain.Audience;
import com.active.presentation.core.repository.AudienceRepository;
import com.active.presentation.ox.domain.OxDashBoard;
import com.active.presentation.ox.domain.OxHistory;
import com.active.presentation.ox.domain.message.OxAnswerMessage;
import com.active.presentation.ox.domain.message.OxResponseMessage;
import com.active.presentation.ox.repository.OxDashBoardRepository;
import com.active.presentation.ox.repository.OxHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.Date;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/10/14
 */
@Controller
public class OxSocketController {

    private static final Logger logger = LoggerFactory.getLogger(OxSocketController.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private OxHistoryRepository oxHistoryRepository;

    @Autowired
    private AudienceRepository audienceRepository;

    @Autowired
    private OxDashBoardRepository oxDashBoardRepository;

    @SubscribeMapping("/players/answer/{boardId}")
    public OxResponseMessage answerResponse(@DestinationVariable Long boardId) {
        OxDashBoard oxDashBoard = findOxDashBoard(boardId);
        return new OxResponseMessage(oxHistoryRepository.countByOxDashBoardAndResult(oxDashBoard, "o"),
                oxHistoryRepository.countByOxDashBoardAndResult(oxDashBoard, "x"));
    }

    @MessageMapping("/answer/{boardId}")
    public void answer(@DestinationVariable Long boardId, OxAnswerMessage answer) {
        Audience audience = generateAudience(answer.getUid());
        OxDashBoard oxDashBoard = findOxDashBoard(boardId);
        OxHistory history = oxHistoryRepository.findByOxDashBoardAndAudience(oxDashBoard, audience);
        if(history != null) {
            history.setResult(answer.getResponse());
            history.setModifyDate(new Date());
            oxHistoryRepository.save(history);
        } else {
            oxHistoryRepository.save(new OxHistory(oxDashBoard, audience, answer.getResponse()));
        }

        OxResponseMessage oxResponseMessage = new OxResponseMessage(oxHistoryRepository.countByOxDashBoardAndResult(oxDashBoard, "o"),
                                                oxHistoryRepository.countByOxDashBoardAndResult(oxDashBoard, "x"), answer);
        messagingTemplate.convertAndSend("/ox/players/answer/" + oxDashBoard.getId(), oxResponseMessage);
    }

    private Audience generateAudience(String uid) {
        Audience audience = audienceRepository.findByUserKey(uid);
        if(audience == null) {
            audience = audienceRepository.save(new Audience(uid));
        }
        return audience;
    }

    private OxDashBoard findOxDashBoard(Long id) {
        OxDashBoard dashBoard = oxDashBoardRepository.findOne(id);
        if(dashBoard == null) {
            throw new NullPointerException();
        }
        return dashBoard;
    }


}
