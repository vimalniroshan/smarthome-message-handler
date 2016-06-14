package com.sparg.java.smarthome.handler;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

import com.sparg.java.smarthome.message.model.ChangeLightState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by vimalniroshan on 6/14/16.
 */
public class DeviceHandler {

    private static final Logger log = LoggerFactory.getLogger(DeviceHandler.class);

    /*private static final String commandTemplate = "./send %s";

    private static Map<LightName, String> deviceNameToDeviceCode = new HashMap<LightName, String>();

    private static Map<String, Map<LightState, String>> deviceStateCodeMapping = new HashMap<String, Map<LightState, String>>();

    static {
        deviceNameToDeviceCode.put(LightName.LIVING_ROOM_LIGHT, "1");
        deviceNameToDeviceCode.put(LightName.BED_ROOM_LIGHT, "2");

        deviceStateCodeMapping.put("1", new HashMap<LightState, String>());
        deviceStateCodeMapping.get("1").put(LightState.ON, "1381683");
        deviceStateCodeMapping.get("1").put(LightState.OFF, "1381692");

        deviceStateCodeMapping.put("2", new HashMap<LightState, String>());
        deviceStateCodeMapping.get("2").put(LightState.ON, "1381827");
        deviceStateCodeMapping.get("2").put(LightState.OFF, "1381836");
    }*/

    private static final Properties deviceHandlerConfig = System.getProperties();

    static {
        try {
            System.getProperties().load(Thread.currentThread().getContextClassLoader().getResourceAsStream("smarthome-handler.properties"));
        } catch (IOException e) {
            log.error("Unable to load properties");
        }
    }

    public static void executeCommand(ChangeLightState changeLightState) {
        MessageFormat commadForm = new MessageFormat(deviceHandlerConfig.getProperty("light.state.change.command.template"));
        String deviceCode = deviceHandlerConfig.getProperty(changeLightState.getLightName().name());
        String stateCode = deviceHandlerConfig.getProperty(deviceCode + "." + changeLightState.getLightState().name());

        String command = commadForm.format(new Object[] {stateCode});

        log.info("Executing command {}", command);

        try {
            Runtime.getRuntime().exec(command);
            log.info("Command successfully executed");
        } catch (IOException e) {
            log.error("Error executing command {}", command);
        }
    }

}
