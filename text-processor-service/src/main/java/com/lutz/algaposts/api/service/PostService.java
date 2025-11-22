package com.lutz.algaposts.api.service;

import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.lutz.algaposts.api.dto.PostProcessorInput;
import com.lutz.algaposts.api.dto.PostProcessorOutput;
import com.lutz.algaposts.infrastructure.rabbitmq.RabbitMQConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final WordService wordService;
    private final RabbitTemplate rabbitTemplate;

    public PostProcessorOutput processPost(PostProcessorInput input) {
        int words = wordService.countWords(input.body());
        Double totalCost = wordService.calculateCost(words);

        return new PostProcessorOutput(input.postId(), words, totalCost);
    }

    public void sendProcessedPost(PostProcessorOutput output) {
        MessagePostProcessor messagePostProcessor = message -> {
            message.getMessageProperties().setHeader("postId", output.postId());
            return message;
        };

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.DIRECT_EXCHANGE_NAME,
                RabbitMQConfig.PROCESSED_POST_ROUTING_KEY,
                output,
                messagePostProcessor);

    }
}
