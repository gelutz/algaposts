package com.lutz.algaposts.api.dto;

import java.util.UUID;

public record PostProcessorInput(UUID postId, String body) {

}
