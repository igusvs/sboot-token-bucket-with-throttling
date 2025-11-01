package com.token.bucket.consumer;

import com.token.bucket.domain.QueueMessage;
import com.token.bucket.service.TokenBucket;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumer {

    private final TokenBucket bucket;

    public QueueConsumer(TokenBucket bucket) {
        this.bucket = bucket;
    }

    @SqsListener(value = "queue-teste")
    public void consumer(QueueMessage payload){


        System.out.println(    bucket.incrementCounter(payload.getDocumento()));





    }
}
