package com.lutz.algaposts.api.controller.dto;

import java.util.UUID;

import lombok.Builder;

@Builder
public record PostOutput(
        UUID id,
        String title,
        String body,
        String author,
        int wordCount,
        double calculatedValue) {
}
