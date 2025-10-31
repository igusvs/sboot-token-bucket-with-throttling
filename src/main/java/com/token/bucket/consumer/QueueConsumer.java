package com.token.bucket.consumer;

import org.springframework.messaging.handler.annotation.Payload;

public class QueueConsumer {



    public void consumer(@Payload String payload){

        System.out.println(payload);

    }
}
