package com.eventhub.api.dto;

public record FieldErrorResponse(
        String field,
        String code,
        String message
) {
}
