package com.bootcamp.smarthome.exception;

public class DeviceOfflineException extends HomeAutomationException {
    public DeviceOfflineException(String field) {
        super("Device " + field + " is offline: ");
    }
}
