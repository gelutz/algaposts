package com.lutz.algaposts.api.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lutz.algaposts.api.dto.PostProcessorInput;
import com.lutz.algaposts.api.dto.PostProcessorOutput;
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
    @RabbitListener(queues = RabbitMQConfig.POST_TO_PROCESS_QUEUE, concurrency = "2-3")
    public void handlePostReceived(@Payload PostProcessorInput input) {
        log.info("Post received to be processed: {}", input.postId());
        PostProcessorOutput output = postService.processPost(input);

        log.info("Post {} processed with {} words and a cost of $ {}",
                output.postId(),
                output.wordCount(),
                output.calculatedValue());
        postService.sendProcessedPost(output);
    }
}
