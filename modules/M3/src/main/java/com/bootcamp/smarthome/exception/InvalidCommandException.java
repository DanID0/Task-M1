package com.bootcamp.smarthome.exception;

public class InvalidCommandException extends HomeAutomationException {
    public InvalidCommandException(String field, Object value, String constraint) {
        super("invalid " + field + " value: " + value + " (" + constraint + ")");
    }
}
