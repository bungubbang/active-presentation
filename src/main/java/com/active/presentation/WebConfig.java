package com.active.presentation;

import com.active.presentation.repository.SpeakerRepository;
import com.active.presentation.security.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/19/14
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @Autowired
    private SpeakerRepository speakerRepository;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInterceptor(usersConnectionRepository, speakerRepository));
    }
}
