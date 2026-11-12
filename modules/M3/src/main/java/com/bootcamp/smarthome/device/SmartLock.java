package com.bootcamp.smarthome.device;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bootcamp.smarthome.exception.HomeAutomationException;
import com.bootcamp.smarthome.exception.InvalidCommandException;

/**
 * A PIN-protected smart door lock.
 * <p>
 * The lock can be locked or unlocked via a 4-digit PIN.
 * Every failed unlock attempt is treated as a security event.
 */
public class SmartLock extends Device {
    private static final Logger logger = LoggerFactory.getLogger(SmartLock.class);
    private boolean isLocked;
    private final String storedPin;

    public SmartLock(String deviceId, String name, boolean isOnline, String pin) {
        super(deviceId, name, isOnline);
        this.isLocked = true;
        this.storedPin = pin;
    }

    // -------------------------------------------------------------------------
    // Device-specific behaviour
    // -------------------------------------------------------------------------

    /**
     * Validates the supplied PIN against the stored PIN.
     */
    public void validatePin(String pin) throws InvalidCommandException {
        if (pin == null || !pin.equals(storedPin)) {
            logger.error("SECURITY ALERT: Failed unlock attempt: deviceId={}", getDeviceId());
            throw new InvalidCommandException("pin", "REDACTED", "is incorrect");
        }
        isLocked = false;
        logger.info(" name={} Unlocked successfully", getName());
    }

    public void lock() {
        isLocked = true;
        logger.info("name={} locked", getName());
    }

    @Override
    public void executeCommand(String command) throws HomeAutomationException {
        if (command.startsWith("UNLOCK")) {
            String[] parts = command.split(" ");
            String pin = (parts.length > 1) ? parts[1] : null;
            validatePin(pin);
        } else if (command.equals("LOCK")) {
            lock();
        } else if (command.equals("TURN_ON")) {
            turnOn();
        } else if (command.equals("TURN_OFF")) {
            turnOff();
        } else {
            throw new HomeAutomationException("Unknown command for SmartLock '" + getName() + "': " + command);
        }
    }

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    public boolean isLocked() {
        return isLocked;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | locked=%b", isLocked);
    }
}
