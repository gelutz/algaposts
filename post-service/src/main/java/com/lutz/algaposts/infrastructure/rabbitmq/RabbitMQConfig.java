package com.lutz.algaposts.infrastructure.rabbitmq;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	public static final String FANOUT_EXCHANGE_NAME = "text-processor-service.post-received.v1.q";

	public static final String POST_TO_PROCESS_QUEUE = "text-processor-service.post-processing.v1.q";
	public static final String POST_TO_PROCESS_DLQ = "text-processor-service.post-processing.v1.dlq";
	public static final String PROCESSED_POST_QUEUE = "post-service.post-processing-result.v1.q";
	public static final String PROCESSED_POST_DLQ = "post-service.post-processing-result.v1.dlq";

	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}

	@Bean
	public Queue postToProcessDeadLetterQueue() {
		return QueueBuilder
				.durable(POST_TO_PROCESS_DLQ)
				.build();
	}

	@Bean
	public Queue processedPostDeadLetterQueue() {
		return QueueBuilder
				.durable(POST_TO_PROCESS_DLQ)
				.build();
	}

	@Bean
	public Queue postToProcessQueue() {
		Map<String, Object> arguments = new HashMap<>();
		arguments.put("x-dead-letter-exchange", "");
		arguments.put("x-dead-letter-routing-key", POST_TO_PROCESS_DLQ);

		return QueueBuilder
				.durable(POST_TO_PROCESS_QUEUE)
				.withArguments(arguments)
				.build();
	}

	@Bean
	public Queue processedPostQueue() {
		Map<String, Object> arguments = new HashMap<>();
		arguments.put("x-dead-letter-exchange", "");
		arguments.put("x-dead-letter-routing-key", PROCESSED_POST_DLQ);

		return QueueBuilder
				.durable(PROCESSED_POST_QUEUE)
				.withArguments(arguments)
				.build();
	}

	// usado apenas como referência para o binding
	public FanoutExchange exchange() {
		return ExchangeBuilder.fanoutExchange(FANOUT_EXCHANGE_NAME).build();
	}

	@Bean
	public Binding bingProcesstemperature() {
		return BindingBuilder.bind(postToProcessQueue()).to(exchange());
	}

	@Bean
	public Binding bindAlert() {
		return BindingBuilder.bind(processedPostQueue()).to(exchange());
	}
}
