package com.lutz.algaposts.api.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lutz.algaposts.api.dto.processor.PostProcessorOutput;
import com.lutz.algaposts.api.service.PostService;
import com.lutz.algaposts.infrastructure.rabbitmq.RabbitMQConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQListener {
	private final PostService postService;

	@Transactional
	@RabbitListener(queues = RabbitMQConfig.PROCESSED_POST_QUEUE, concurrency = "2-3")
	public void handlePostProcessed(@Payload PostProcessorOutput output) {
		log.info("Post processed: {}, saving...", output.postId());
		postService.handleProcessedPost(output);
	}
}
