package com.lutz.algaposts.api.dto.processor;

import java.util.UUID;

public record PostProcessorOutput(
                UUID postId,
                int wordCount,
                Double calculatedValue) {

}
