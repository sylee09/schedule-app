package org.zerock.scheduleapp.exception;

public class RepliesLimitException extends RuntimeException {
    public RepliesLimitException(String message) {
        super(message);
    }
}
