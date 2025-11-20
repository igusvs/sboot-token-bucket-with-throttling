package com.token.bucket.controller;

import com.token.bucket.domain.PagamentoMessage;
import com.token.bucket.domain.PublishType;
import com.token.bucket.service.strategy.SqsPublishStrategyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
public class PagamentoController {

    private final SqsPublishStrategyFactory strategyFactory;
    final Logger logger = LoggerFactory.getLogger(getClass().getName());

    public PagamentoController(SqsPublishStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }


    @PostMapping("/pagar")
    public ResponseEntity<Object> produce(@RequestBody PagamentoMessage pagamentoMessage){
        logger.info("m=PagamentoController.produce, msg=Ordem de pagamento recebido={}",
                Map.of("documento", pagamentoMessage.getDocumento(),
                        "ordem", pagamentoMessage.getFormaPagamento()));

        final var strategy = strategyFactory.getStrategy(PublishType.QUEUE_HOT);
        strategy.publish(pagamentoMessage.gerarListaPagamentos());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
