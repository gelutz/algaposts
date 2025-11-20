package com.lutz.algaposts.domain.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
public class Post {
    @Id
    private UUID id;

    private String title;
    private String body;
    private String author;
    private int wordCount;
    private int calculatedValue;
}
