package com.eduvault.common;

import java.time.Instant;

// Esse será o formato padronizado de erro da nossa API REST
public record ErrorResponse(String message, String error, int status, Instant timestamp) {
    public ErrorResponse(String message, String error, int status) {
        this(message, error, status, Instant.now());
    }
}
