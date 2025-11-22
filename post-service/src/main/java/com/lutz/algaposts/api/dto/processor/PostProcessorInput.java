package com.lutz.algaposts.api.dto.processor;

import java.util.UUID;

public record PostProcessorInput(UUID postId, String body) {

}
