package com.lutz.algaposts.api.controller;

import java.util.UUID;

import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lutz.algaposts.api.controller.dto.PostInput;
import com.lutz.algaposts.domain.model.Post;
import com.lutz.algaposts.infrastructure.rabbitmq.RabbitMQConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    // private final PostService postService;
    private final RabbitTemplate rabbitTemplate;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void receivePost(@RequestBody PostInput input) {
        log.info("Receiving post from author {}...", input.author());

        var post = Post.builder()
                .id(UUID.randomUUID())
                .author(input.author())
                .body(input.body())
                .build();

        MessagePostProcessor messagePostProcessor = message -> {
            message.getMessageProperties().setHeader("postId", post.getId());
            return message;
        };

        String routingKey = ""; // precisa no mínimo ser uma string vazia pra funcionar caso não tenha nada
                                // (acho)
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.FANOUT_EXCHANGE_NAME,
                routingKey,
                post,
                messagePostProcessor);
    }
}
