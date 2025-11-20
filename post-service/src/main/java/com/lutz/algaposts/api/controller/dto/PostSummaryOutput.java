package com.lutz.algaposts.api.controller.dto;

import java.util.UUID;

import lombok.Builder;

@Builder
public record PostSummaryOutput(
                UUID id,
                String title,
                String summary,
                String author) {
}
