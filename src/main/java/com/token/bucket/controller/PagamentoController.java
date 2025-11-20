package com.token.bucket.controller;

import com.token.bucket.domain.PagamentoMessage;
import com.token.bucket.domain.PublishType;
import com.token.bucket.service.QueuePublish;
import com.token.bucket.service.strategy.SqsPublishStrategyFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PagamentoController {

    private final QueuePublish queuePublish;
    private final SqsPublishStrategyFactory strategyFactory;

    public PagamentoController(QueuePublish queuePublish, SqsPublishStrategyFactory strategyFactory) {
        this.queuePublish = queuePublish;
        this.strategyFactory = strategyFactory;
    }


    @PostMapping("/queue")
    public ResponseEntity<Object> produce(@RequestBody PagamentoMessage pagamentoMessage){


        final var strategy = strategyFactory.getStrategy(PublishType.QUEUE_HOT);
        strategy.publish(pagamentoMessage.gerarListaPagamentos());

        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

}
