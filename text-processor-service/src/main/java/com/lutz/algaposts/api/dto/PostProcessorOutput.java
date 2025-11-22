package com.lutz.algaposts.api.dto;

import java.util.UUID;

public record PostProcessorOutput(
        UUID postId,
        int wordCount,
        Double calculatedValue) {

}
