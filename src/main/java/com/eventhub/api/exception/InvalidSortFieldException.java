package com.eventhub.api.exception;

public class InvalidSortFieldException extends EventHubException {
    private final String field;

    public InvalidSortFieldException(String field) {
        super(
                "INVALID_SORT_FIELD",
                "Sorting by field '" + field + "' is not allowed"
        );
        this.field = field;
    }
}
