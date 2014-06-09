package com.active.presentation.ox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/9/14
 */
@Controller
public class OxController {

    private static final Logger logger = LoggerFactory.getLogger(OxController.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @RequestMapping("/ox")
    public String ox() {
        return "ox";
    }

    @RequestMapping("/ox/dashboard")
    public String oxDashBoard() {
        return "ox/ox-dashboard";
    }

    @RequestMapping("/ox/controller")
    public String oxController() {
        return "ox/ox-controller";
    }

    @MessageMapping("/answer")
    public void answer(AnswerMessage message) {
        logger.info("message = {}", message.isAnswer());
        messagingTemplate.convertAndSend("/ox/players/answer", message);
    }

    static class AnswerMessage {

        private boolean answer;

        public boolean isAnswer() { return answer; }
        public void setAnswer(boolean answer) { this.answer = answer; }

    }
}
