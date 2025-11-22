package com.lutz.algaposts.api.dto;

import java.util.UUID;

import lombok.Builder;

@Builder
public record PostSummaryOutput(
        UUID id,
        String title,
        String summary,
        String author) {
}
