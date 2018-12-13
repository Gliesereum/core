package com.gliesereum.mail.config;

import com.gliesereum.mail.mq.RedisMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.HashSet;
import java.util.Set;

/**
 * @author vitalij
 * @since 10/19/18
 */
@Configuration
public class RedisConfiguration {

    private final String CHANEL = "spring.mail.chanel-verification";

    @Autowired
    private Environment environment;

    @Bean
    public RedisMessageListenerContainer redisContainer(RedisMessageListener redisMessageListener,
                                                        RedisConnectionFactory redisConnectionFactory,
                                                        Set<ChannelTopic> channels) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(redisMessageListener, channels);
        return container;
    }

    @Bean
    public Set<ChannelTopic> channelTopic() {
        Set<ChannelTopic> channels = new HashSet<>();
        channels.add(new ChannelTopic(environment.getRequiredProperty(CHANEL)));
        return channels;
    }


}