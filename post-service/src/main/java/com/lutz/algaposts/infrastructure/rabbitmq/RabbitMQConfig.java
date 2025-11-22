package com.lutz.algaposts.infrastructure.rabbitmq;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	public static final String DIRECT_EXCHANGE_NAME = "algaposts.direct.v1";
	public static final String POST_TO_PROCESS_ROUTING_KEY = "post.to.process";
	public static final String PROCESSED_POST_ROUTING_KEY = "post.processed";

	public static final String POST_TO_PROCESS_QUEUE = "text-processor-service.post-processing.v1.q";
	public static final String POST_TO_PROCESS_DLQ = "text-processor-service.post-processing.v1.dlq";
	public static final String PROCESSED_POST_QUEUE = "post-service.post-processing-result.v1.q";
	public static final String PROCESSED_POST_DLQ = "post-service.post-processing-result.v1.dlq";

	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}

	public Queue postToProcessDeadLetterQueue() {
		return QueueBuilder
				.durable(POST_TO_PROCESS_DLQ)
				.build();
	}

	public Queue processedPostDeadLetterQueue() {
		return QueueBuilder
				.durable(PROCESSED_POST_DLQ)
				.build();
	}

	public Queue postToProcessQueue() {
		Map<String, Object> arguments = new HashMap<>();
		arguments.put("x-dead-letter-exchange", "");
		arguments.put("x-dead-letter-routing-key", POST_TO_PROCESS_DLQ);

		return QueueBuilder
				.durable(POST_TO_PROCESS_QUEUE)
				.withArguments(arguments)
				.build();
	}

	public Queue processedPostQueue() {
		Map<String, Object> arguments = new HashMap<>();
		arguments.put("x-dead-letter-exchange", "");
		arguments.put("x-dead-letter-routing-key", PROCESSED_POST_DLQ);

		return QueueBuilder
				.durable(PROCESSED_POST_QUEUE)
				.withArguments(arguments)
				.build();
	}

	public DirectExchange directExchange() {
		return ExchangeBuilder.directExchange(DIRECT_EXCHANGE_NAME).build();
	}

	public Binding bindPostToProcess() {
		return BindingBuilder
				.bind(postToProcessQueue())
				.to(directExchange())
				.with(POST_TO_PROCESS_ROUTING_KEY);
	}

	public Binding bindProcessedPost() {
		return BindingBuilder
				.bind(processedPostQueue())
				.to(directExchange())
				.with(PROCESSED_POST_ROUTING_KEY);
	}
}
