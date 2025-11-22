package com.lutz.algaposts.api.config.jackson;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JacksonConfiguration {
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper om) {
        return new Jackson2JsonMessageConverter(om);
    }
}
