package com.bootcamp.smarthome.device;

import com.bootcamp.smarthome.exception.HomeAutomationException;
import com.bootcamp.smarthome.exception.InvalidValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A dimmable smart light bulb.
 * <p>
 * Supports brightness control in the range [0, 100] where
 * 0 = fully off (dimmed) and 100 = fully bright.
 */
public class SmartLight extends Device {
    private static final Logger logger = LoggerFactory.getLogger(SmartLight.class);

    /**
     * Brightness level. Valid range: 0–100 inclusive.
     */
    private int brightness;

    public SmartLight(String deviceId, String name, boolean isOnline) {
        super(deviceId, name, isOnline);
        this.brightness = 50;
    }

    // -------------------------------------------------------------------------
    // Device-specific behaviour
    // -------------------------------------------------------------------------

    /**
     * Sets the brightness of this light.
     * <p>
     * Valid range: 0–100 inclusive.
     */
    public void setBrightness(int level) throws InvalidValueException {
        if (level > 100 || level < 0) {
            throw new InvalidValueException("brightness", level, "must be between 0 and 100");
        }
        this.brightness = level;
        logger.info("name={} brightness set to level={} %", getName(), level);
    }

    @Override
    public void executeCommand(String command) throws HomeAutomationException {
        if (command.startsWith("SET_BRIGHTNESS")) {
            String[] parts = command.split(" ");
            int level = (parts.length > 1) ? Integer.parseInt(parts[1]) : 50;
            setBrightness(level);
        } else if (command.equals("TURN_ON")) {
            turnOn();
        } else if (command.equals("TURN_OFF")) {
            turnOff();
        } else {
            throw new HomeAutomationException("Unknown command for SmartLight '" + getName() + "': " + command);
        }
    }

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    public int getBrightness() {
        return brightness;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | brightness=%3d%%", brightness);
    }
}
