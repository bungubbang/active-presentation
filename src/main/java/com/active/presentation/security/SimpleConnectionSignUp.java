package com.active.presentation.security;

import com.active.presentation.domain.Speaker;
import com.active.presentation.repository.SpeakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/19/14
 */
public class SimpleConnectionSignUp implements ConnectionSignUp {

    @Autowired
    private SpeakerRepository speakerRepository;

    @Override
    public String execute(Connection<?> connection) {
        Speaker speaker = speakerRepository.findByEmail(connection.fetchUserProfile().getEmail());
        if(speaker == null) {
            speaker = new Speaker(connection.fetchUserProfile().getName(), connection.fetchUserProfile().getEmail(), connection.getImageUrl());
            speakerRepository.save(speaker);
        }
        return String.valueOf(speaker.getId());
    }
}
