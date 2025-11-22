package com.lutz.algaposts.infrastructure.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class RabbitMQInitializer {
    private final RabbitAdmin rabbitAdmin;

    @PostConstruct
    public void init() {
        rabbitAdmin.initialize();
    }
}
