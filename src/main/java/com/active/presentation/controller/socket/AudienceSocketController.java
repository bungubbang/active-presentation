package com.active.presentation.controller.socket;

import com.active.presentation.domain.PresentationDashboard;
import com.active.presentation.domain.message.AnswerAudienceListMessage;
import com.active.presentation.repository.AnswerRepository;
import com.active.presentation.service.SocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 2014. 8. 22.
 */
@Controller
public class AudienceSocketController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private SocketService socketService;

    @SubscribeMapping("/players/answer/audience/{boardId}")
    public List<AnswerAudienceListMessage> audience(@DestinationVariable Long boardId) {
        PresentationDashboard dashboard = socketService.findOxDashBoard(boardId);
        return answerRepository.findByAnswerAudienceList(dashboard, false);

    }
}
