package com.token.bucket.controller;

import com.token.bucket.domain.QueueMessage;
import com.token.bucket.service.QueuePublish;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Controller("/")
public class QueueController {

    private final QueuePublish queuePublish;

    public QueueController(QueuePublish queuePublish) {
        this.queuePublish = queuePublish;
    }


    @PostMapping("/queue")
    public ResponseEntity<Object> produce(@RequestBody QueueMessage queueMessage){

        System.out.println(queueMessage);

        List<QueueMessage> queueMessageList = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            final var item = new QueueMessage();
            item.setContador(i);
            item.setDocumento(queueMessage.getDocumento());
            item.setOperacao(queueMessage.getOperacao());

            queueMessageList.add(item);
        }

        queuePublish.publishQueue(queueMessageList);

        return ResponseEntity.accepted().build();
    }

}
