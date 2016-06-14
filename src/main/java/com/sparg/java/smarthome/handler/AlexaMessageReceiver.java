package com.sparg.java.smarthome.handler;

import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.sparg.java.smarthome.message.model.ChangeLightState;
import com.sparg.java.smarthome.message.utils.JSONUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by vimalniroshan on 6/13/16.
 */
public class AlexaMessageReceiver {

    private static final Logger log = LoggerFactory.getLogger(AlexaMessageReceiver.class);

    private static final String MESSAGE_QUEUE = "Alexa-Message-Queue";
    private AmazonSQS sqs;
    private String messageQueueUrl;

    private void init() {
        /*
         * The ProfileCredentialsProvider will return your [default]
         * credential profile by reading from the credentials file located at
         * (~/.aws/credentials).
         */
        AWSCredentials credentials = null;
        try {
            credentials = new ProfileCredentialsProvider().getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                            "Please make sure that your credentials file is at the correct " +
                            "location (~/.aws/credentials), and is in valid format.",
                    e);
        }

        sqs = new AmazonSQSClient(credentials);
        messageQueueUrl = sqs.getQueueUrl(MESSAGE_QUEUE).getQueueUrl();

    }

    public AlexaMessageReceiver() {
        init();
    }

    public ChangeLightState receiveMessage() {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(messageQueueUrl);
        receiveMessageRequest.setMaxNumberOfMessages(1);
        receiveMessageRequest.setWaitTimeSeconds(20);

        List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();

        if(CollectionUtils.isNotEmpty(messages)) {
            Message message = messages.get(0);
            log.info("Message {} received : {}", messages.size(), message.getBody());
            String messageReceiptHandle = message.getReceiptHandle();
            sqs.deleteMessage(new DeleteMessageRequest(messageQueueUrl, messageReceiptHandle));
            return JSONUtil.toObject(message.getBody(), ChangeLightState.class);
        }

        return null;
    }
}
