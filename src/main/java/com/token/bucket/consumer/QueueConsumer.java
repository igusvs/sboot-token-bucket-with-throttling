package com.token.bucket.consumer;

import com.token.bucket.domain.QueueMessage;
import com.token.bucket.service.ThrottlingService;
import com.token.bucket.service.TokenBucketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumer {

    Logger logger = LoggerFactory.getLogger(getClass().getName());

    private final ThrottlingService service;

    public QueueConsumer(ThrottlingService service) {
        this.service = service;
    }


    @SqsListener(value = "queue-hot")
    public void consumer(QueueMessage payload){

        logger.info("m=consumer, msg=message recebida documento={}", payload.getDocumento());

        service.verify(payload);


    }
}
