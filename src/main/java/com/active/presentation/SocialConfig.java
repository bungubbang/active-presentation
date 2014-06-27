package com.active.presentation;

import com.active.presentation.security.SecurityContext;
import com.active.presentation.security.SimpleConnectionSignUp;
import com.active.presentation.security.SimpleSignInAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;

import javax.sql.DataSource;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/17/14
 */
@Configuration
@EnableSocial
public class SocialConfig implements SocialConfigurer {

    @Autowired
    private DataSource dataSource;

    @Value("${application.url}")
    private String appUrl;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
        cfConfig.addConnectionFactory(new FacebookConnectionFactory(env.getProperty("facebook.appKey"), env.getProperty("facebook.appSecret")));
    }


    /**
     * Singleton data access object providing access to connections across all users.
     */
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        repository.setConnectionSignUp(simpleConnectionSignUp());
        return repository;
    }

    public UserIdSource getUserIdSource() {
        return new UserIdSource() {
            @Override
            public String getUserId() {
                return String.valueOf(SecurityContext.getCurrentUser().getId());
            }
        };
    }

    @Bean
    @Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)
    public Facebook facebook(ConnectionRepository repository) {
        Connection<Facebook> connection = repository.findPrimaryConnection(Facebook.class);
        return connection != null ? connection.getApi() : null;
    }

    @Bean
    public ProviderSignInController providerSignInController(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository usersConnectionRepository) throws Exception {

        final ProviderSignInController signInController = new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, simpleSignInAdapter()) {
            @Override
            public void afterPropertiesSet() throws Exception {
                super.afterPropertiesSet();
                setApplicationUrl(appUrl);
                setPostSignInUrl("/admin");
            }
        };
        return signInController;
    }

    @Bean
    public SimpleConnectionSignUp simpleConnectionSignUp() {
        return new SimpleConnectionSignUp();
    }

    @Bean
    public SimpleSignInAdapter simpleSignInAdapter() {
        return new SimpleSignInAdapter();
    }

}
