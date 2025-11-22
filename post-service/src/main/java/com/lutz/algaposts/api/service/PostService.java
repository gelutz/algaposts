package com.lutz.algaposts.api.service;

import java.util.Objects;
import java.util.UUID;

import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lutz.algaposts.api.dto.PostInput;
import com.lutz.algaposts.api.dto.processor.PostProcessorInput;
import com.lutz.algaposts.api.dto.processor.PostProcessorOutput;
import com.lutz.algaposts.api.repository.PostRepository;
import com.lutz.algaposts.domain.exceptions.BadInputException;
import com.lutz.algaposts.domain.exceptions.ResourceNotFoundException;
import com.lutz.algaposts.domain.model.Post;
import com.lutz.algaposts.infrastructure.rabbitmq.RabbitMQConfig;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final RabbitTemplate rabbitTemplate;

    public Post create(@NonNull PostInput input) {
        if (input.body() == null || input.author() == null) {
            throw new BadInputException("Post body and author are required");
        }

        Post post = Post.builder()
                .id(UUID.randomUUID())
                .author(input.author())
                .body(input.body())
                .build();

        this.sendPostToProcessor(new PostProcessorInput(post.getId(), post.getBody()));

        return postRepository.save(Objects.requireNonNull(post));
    }

    public void sendPostToProcessor(PostProcessorInput post) {
        MessagePostProcessor messagePostProcessor = message -> {
            message.getMessageProperties().setHeader("postId", post.postId());
            return message;
        };

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.DIRECT_EXCHANGE_NAME,
                RabbitMQConfig.POST_TO_PROCESS_ROUTING_KEY,
                post,
                messagePostProcessor);
    }

    public void handleProcessedPost(PostProcessorOutput output) {
        Post originalPost = findById(output.postId());

        if (originalPost == null) {
            throw new ResourceNotFoundException();
        }

        originalPost.setCalculatedValue(output.calculatedValue());
        originalPost.setWordCount(output.wordCount());
        postRepository.save(originalPost);
    }

    public Post findById(@NonNull java.util.UUID id) {
        return postRepository.findById(id).orElse(null);
    }

    public Page<Post> list(@NonNull Pageable pageable) {
        return postRepository.findAll(pageable);
    }
}
