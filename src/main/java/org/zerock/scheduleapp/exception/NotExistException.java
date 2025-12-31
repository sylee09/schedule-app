package org.zerock.scheduleapp.exception;

public class NotExistException extends RuntimeException {
    public NotExistException(String message) {
        super(message);
    }
}
