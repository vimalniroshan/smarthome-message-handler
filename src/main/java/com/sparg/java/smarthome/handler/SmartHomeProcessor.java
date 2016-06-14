package com.sparg.java.smarthome.handler;

import com.sparg.java.smarthome.message.model.ChangeLightState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by vimalniroshan on 6/13/16.
 */
public class SmartHomeProcessor {

    private static final Logger log = LoggerFactory.getLogger(SmartHomeProcessor.class);

    public static void main(String[] args) {

        AlexaMessageReceiver alexaMessageReceiver = new AlexaMessageReceiver();
        while(true) {
            ChangeLightState changeLightState = alexaMessageReceiver.receiveMessage();

            if (changeLightState != null) {
                log.info("Message Received %s, %s", changeLightState.getLightName(), changeLightState.getLightState());
                performAction(changeLightState);
            } else {
                log.info("No message received");
            }
        }
    }

    private static void performAction(ChangeLightState changeLightState) {

        try {
            Runtime.getRuntime().exec(DeviceConfigMapping.getTargetCommand(changeLightState.getLightName(),
                    changeLightState.getLightState()));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
