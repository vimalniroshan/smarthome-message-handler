package com.sparg.java.smarthome.handler;

import com.sparg.java.smarthome.message.model.LightName;
import com.sparg.java.smarthome.message.model.LightState;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vimalniroshan on 6/14/16.
 */
public class DeviceConfigMapping {

    private static final String commandTemplate = "./send %s %s %s";

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
    }

    public static String getTargetCommand(LightName lightName, LightState lightState) {
        return String.format(commandTemplate, deviceStateCodeMapping.get(deviceNameToDeviceCode.get(lightName)).get(lightState));
    }

}
