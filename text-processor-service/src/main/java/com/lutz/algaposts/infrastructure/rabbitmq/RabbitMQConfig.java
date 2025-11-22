package com.lutz.algaposts.infrastructure.rabbitmq;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String DIRECT_EXCHANGE_NAME = "algaposts.direct.v1";
    public static final String PROCESSED_POST_ROUTING_KEY = "post.processed";

    public static final String POST_TO_PROCESS_QUEUE = "text-processor-service.post-processing.v1.q";

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
}
