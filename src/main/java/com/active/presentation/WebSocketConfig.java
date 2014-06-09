package com.active.presentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurationSupport;
import org.springframework.web.socket.messaging.SubProtocolWebSocketHandler;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/9/14
 */
@EnableWebSocket
@Configuration
public class WebSocketConfig extends WebSocketMessageBrokerConfigurationSupport {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/join").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/ox/");
        registry.setApplicationDestinationPrefixes("/ox");
    }

    @Override
    public WebSocketHandler subProtocolWebSocketHandler() {
        return new TrackingSubProtocolWebSocketHandler(clientInboundChannel(), clientOutboundChannel());
    }


    static class TrackingSubProtocolWebSocketHandler extends SubProtocolWebSocketHandler {


        public TrackingSubProtocolWebSocketHandler(MessageChannel clientInboundChannel, SubscribableChannel clientOutboundChannel) {
            super(clientInboundChannel, clientOutboundChannel);
        }

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            super.afterConnectionEstablished(session);
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
            super.afterConnectionClosed(session, closeStatus);
        }

    }

}
