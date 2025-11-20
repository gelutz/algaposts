package com.lutz.algaposts.api.service;

import org.springframework.stereotype.Service;

import com.lutz.algaposts.api.repository.PostRepository;
import com.lutz.algaposts.domain.model.Post;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post create(@NonNull Post post) {
        return postRepository.save(post);
    }
}
