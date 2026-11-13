package com.bootcamp.smarthome.exception;

public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException(String field) {
        super("Device '" + field + "' was not found.");
    }
}
