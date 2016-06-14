package com.sparg.java.smarthome.handler;

import com.sparg.java.smarthome.message.model.ChangeLightState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by vimalniroshan on 6/13/16.
 */
public class SmartHomeProcessor {

    private static final Logger log = LoggerFactory.getLogger(SmartHomeProcessor.class);

    public static void main(String[] args) {

        AlexaMessageReceiver alexaMessageReceiver = new AlexaMessageReceiver();
        log.info("Started listening for messages from Alexa...");
        while(true) {
            log.info("Polling for messages from Alexa");
            ChangeLightState changeLightState = alexaMessageReceiver.receiveMessage();

            if (changeLightState != null) {
                log.info("Message Received {} {}", changeLightState.getLightName(), changeLightState.getLightState());
                log.info("Trying to take action based on message");
                performAction(changeLightState);
            } else {
                log.info("No message received");
            }
        }
    }

    private static void performAction(ChangeLightState changeLightState) {
        try {
            //Runtime.getRuntime().exec(DeviceConfigMapping.getTargetCommand(changeLightState.getLightName(),
              //      changeLightState.getLightState()));
            log.info("Executed action '{}' on message", DeviceConfigMapping.getTargetCommand(changeLightState.getLightName(),
                    changeLightState.getLightState()));
        } catch (Exception e) {
            log.error("Exception while invoking target command ", e);
        }
    }
}
