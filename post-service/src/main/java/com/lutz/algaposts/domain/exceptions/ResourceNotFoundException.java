package com.lutz.algaposts.domain.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super("Recurso não encontrado");
    }
}
