package com.token.bucket.consumer;

import com.token.bucket.domain.QueueMessage;
import com.token.bucket.service.TokenBucketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class QueueThrottlingConsumer {

    Logger logger = LoggerFactory.getLogger(getClass().getName());

    private final TokenBucketService bucket;

    public QueueThrottlingConsumer(TokenBucketService bucket) {
        this.bucket = bucket;
    }

    @SqsListener(value = "queue-throttling")
    public void consumerThrottling(QueueMessage payload){

        logger.info("m=consumerThrottling, msg=message recebida documento={}", payload.getDocumento());


    }
}
