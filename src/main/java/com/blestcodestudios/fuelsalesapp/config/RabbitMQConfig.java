package com.blestcodestudios.fuelsalesapp.config;


import org.springframework.amqp.core.Queue;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {



    public static final String HOMEPAGE_VISIT_QUEUE = "homepage_visits";

    @Bean
    public Queue homepageVisitQueue() {
        return new Queue(HOMEPAGE_VISIT_QUEUE, true);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}