package com.bootcamp.smarthome.controller;

import com.bootcamp.smarthome.exception.DeviceNotFoundException;
import com.bootcamp.smarthome.exception.DeviceOfflineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bootcamp.smarthome.device.Device;
import com.bootcamp.smarthome.exception.HomeAutomationException;


/**
 * Central hub that manages all registered smart devices.
 * <p>
 * Devices are stored in a fixed-size array (maximum {@value #MAX_DEVICES}).
 * The controller routes commands to devices by their ID.
 */
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    public static final int MAX_DEVICES = 8;

    private final Device[] devices = new Device[MAX_DEVICES];
    private int deviceCount = 0;

    // -------------------------------------------------------------------------
    // Device registration
    // -------------------------------------------------------------------------

    /**
     * Registers a new device with the controller.
     * The controller accepts at most {@value #MAX_DEVICES} devices.
     *
     * @param device the device to register
     * @throws IllegalStateException if the device limit has been reached
     */
    public void addDevice(Device device) {
        if (deviceCount >= MAX_DEVICES) {
            throw new IllegalStateException(
                    "Cannot add device '" + device.getDeviceId() +
                            "': controller is at maximum capacity (" + MAX_DEVICES + ").");
        }
        devices[deviceCount] = device;
        deviceCount++;
        logger.info("Device registered: device={} ", device);
    }

    // -------------------------------------------------------------------------
    // Device lookup
    // -------------------------------------------------------------------------

    /**
     * Finds a registered device by its ID.
     * <p>
     * Returns {@code null} when no matching device is found.
     */
    public Device findDevice(String deviceId) {
        for (int i = 0; i < deviceCount; i++) {
            if (devices[i] != null && devices[i].getDeviceId().equals(deviceId)) {
                return devices[i];
            }
        }
        return null;
    }

    // -------------------------------------------------------------------------
    // Command routing
    // -------------------------------------------------------------------------

    /**
     * Parses {@code fullCommand}, resolves the target device, and delegates
     * execution to {@link Device#executeCommand(String)}.
     * <p>
     * Full command format: {@code "DEVICE_ID ACTION [VALUE]"}
     * Example: {@code "LIGHT_01 SET_BRIGHTNESS 75"}
     *
     * @param fullCommand the full command string
     */

    public void sendCommand(String fullCommand) throws HomeAutomationException {
        String deviceId = "unknown";

        try {
            deviceId = CommandParser.extractDeviceId(fullCommand);
            logger.debug("Processing command: deviceId={}", deviceId);
            String command = CommandParser.extractCommand(fullCommand);

            Device device = findDevice(deviceId);

            if (device == null) {
                logger.warn("Device not found: deviceId={}", deviceId);
                throw new DeviceNotFoundException(deviceId);
            }

            if (!device.isOnline()) {
                logger.warn("Device offline, command skipped: deviceId={}", deviceId);
                throw new DeviceOfflineException(deviceId);
            }
            device.executeCommand(command);
            logger.info("Command executed successfully: deviceId={}", deviceId);
        } catch (DeviceOfflineException e) {
            throw e;
        } catch (DeviceNotFoundException e) {
            throw e;
        } catch (HomeAutomationException e) {
            logger.error("Command execution failed: deviceId={}", deviceId);
            throw new HomeAutomationException("Command failed for device '" + deviceId + "'", e);
        } finally {
            logger.debug("Command processing finished: deviceId={}", deviceId);
        }

    }

    // -------------------------------------------------------------------------
    // Utility
    // -------------------------------------------------------------------------

    /**
     * Prints the status of every registered device.
     */
    public void printAllDevices() {
        logger.debug("Controller state: deviceCount={}, maxDevices={}", deviceCount, MAX_DEVICES);
        for (int i = 0; i < deviceCount; i++) {
            logger.debug("Registered device: index={}, device={}", i, devices[i]);
        }
    }

    public int getDeviceCount() {
        return deviceCount;
    }
}
