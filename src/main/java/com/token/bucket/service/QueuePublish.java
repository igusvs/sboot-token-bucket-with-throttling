package com.token.bucket.service;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
import com.token.bucket.domain.QueueMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class QueuePublish {

    private final AmazonSQSAsync queue;

    public QueuePublish(AmazonSQSAsync queue) {
        this.queue = queue;
    }

    public void publishQueue(List<QueueMessage> messages){


        try {

            List<SendMessageBatchRequestEntry> entries = new ArrayList<>();

/*            SendMessageRequest request = new SendMessageRequest();
            request.setQueueUrl("http://localhost:4566/000000000000/queue-teste");
            request.setMessageBody(JsonConverter.toJson(message));*/

            messages.forEach(item -> {
                final var entry = new SendMessageBatchRequestEntry();
                entry.setMessageBody(JsonConverter.toJson(item));
                entry.setId(UUID.randomUUID().toString());
                entries.add(entry);
            });

            SendMessageBatchRequest batchRequest = new SendMessageBatchRequest();
            batchRequest.setQueueUrl("http://localhost:4566/000000000000/queue-teste");
            batchRequest.setEntries(entries);


            queue.sendMessageBatch(batchRequest);

        }catch (Exception e){
            throw e;
        }
    }
}
