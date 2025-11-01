package com.token.bucket.service;

import com.token.bucket.domain.QueueMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ThrottlingService {

    Logger logger = LoggerFactory.getLogger(getClass().getName());

    final static private Integer limitReq = 5;

    private final TokenBucketService tokenBucketService;
    private final QueuePublish queuePublish;

    public ThrottlingService(TokenBucketService tokenBucketService, QueuePublish queuePublish) {
        this.tokenBucketService = tokenBucketService;
        this.queuePublish = queuePublish;
    }


    public void verify(final QueueMessage message){
        logger.info("m=ThrottlingService.verify, msg=message recebida documento={}", message.getDocumento());

        final var result = tokenBucketService.incrementCounter(message.getDocumento());

        logger.info("m=ThrottlingService.verify, msg=Contador={}", result);

        if(result >= limitReq){
            logger.info("m=ThrottlingService.verify, msg=Envio para throttling RATE LIMIT={}", message.getDocumento() + "=" + message.getContador());
            queuePublish.publishThrottling(message);
            return;
        }

        logger.info("m=ThrottlingService.verify, msg=Consumido com sucesso = Contador={}", result);

    }
}
