package com.lutz.algaposts.api.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lutz.algaposts.api.dto.PostInput;
import com.lutz.algaposts.api.dto.PostOutput;
import com.lutz.algaposts.api.dto.PostSummaryOutput;
import com.lutz.algaposts.api.service.PostService;
import com.lutz.algaposts.domain.model.Post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void receivePost(@RequestBody PostInput input) {
        log.info("Receiving post from author {}...", input.author());
        postService.create(input);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostOutput> getPostById(@PathVariable UUID postId) {
        var post = postService.findById(postId);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }

        var output = new PostOutput(
                post.getId(),
                post.getTitle(),
                post.getBody(),
                post.getAuthor(),
                post.getWordCount(),
                post.getCalculatedValue());

        return ResponseEntity.ok(output);
    }

    @GetMapping
    public ResponseEntity<Page<PostSummaryOutput>> listPosts(@PageableDefault Pageable pageable) {
        Page<Post> posts = postService.list(pageable);

        Page<PostSummaryOutput> summaries = posts.map(p -> {
            String summary = p.getBody().split("\n")[0];
            return new PostSummaryOutput(
                    p.getId(),
                    p.getTitle(),
                    summary,
                    p.getAuthor());
        });

        return ResponseEntity.ok(summaries);
    }
}
