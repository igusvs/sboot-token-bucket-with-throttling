package com.token.bucket.consumer;

import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumer {

    @SqsListener(value = "queue-teste")
    public void consumer(@Payload String payload){
        System.out.println(payload);
    }
}
