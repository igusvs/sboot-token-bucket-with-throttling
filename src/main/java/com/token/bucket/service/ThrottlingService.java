package com.token.bucket.service;

import com.token.bucket.domain.PagamentoMessage;
import com.token.bucket.domain.PublishType;
import com.token.bucket.service.strategy.SqsPublishStrategyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThrottlingService {

    Logger logger = LoggerFactory.getLogger(getClass().getName());

    final static private Integer limitReq = 5;

    private final TokenBucketService tokenBucketService;
    private final SqsPublishStrategyFactory strategyFactory;

    public ThrottlingService(TokenBucketService tokenBucketService, SqsPublishStrategyFactory strategyFactory) {
        this.tokenBucketService = tokenBucketService;
        this.strategyFactory = strategyFactory;
    }


    public void verify(final PagamentoMessage message){
        logger.info("m=ThrottlingService.verify, msg=message recebida documento={}", message.getDocumento());

        final var result = tokenBucketService.incrementCounter(message.getDocumento());

        logger.info("m=ThrottlingService.verify, msg=Contador={}", result);

        if(result >= limitReq){
            logger.info("m=ThrottlingService.verify, msg=Envio para throttling RATE LIMIT={}", message.getDocumento());

            final var strategy = strategyFactory.getStrategy(PublishType.QUEUE_THROTTLING);
            strategy.publish(List.of(message));
            return;
        }

        logger.info("m=ThrottlingService.verify, msg=Consumido com sucesso = Contador={}", result);

    }
}
