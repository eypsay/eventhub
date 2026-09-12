package com.eventhub.api.Exception;

public class EventHubException extends RuntimeException {
    private final String code;

    public EventHubException( String code,String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }


}
