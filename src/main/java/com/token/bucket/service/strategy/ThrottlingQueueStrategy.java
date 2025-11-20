package com.token.bucket.service.strategy;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.token.bucket.domain.PagamentoMessage;
import com.token.bucket.service.JsonConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ThrottlingQueueStrategy implements SqsPublishStrategy {


    private final String queueUrl;
    private final String delayMessage;
    private final AmazonSQSAsync queue;

    public ThrottlingQueueStrategy(@Value("${queue.sqs.throttling.url}") String queueUrl,
                                   @Value("${queue.sqs.throttling.delay}") String delayMessage,
                                   AmazonSQSAsync queue) {
        this.queueUrl = queueUrl;
        this.delayMessage = delayMessage;
        this.queue = queue;
    }

    @Override
    public void publish(List<PagamentoMessage> payload) {
        try {
            final var entries = buildBatchMessage(payload);

            queue.sendMessageBatch(buildBatchRequest(entries));

        }catch (Exception e){
            throw e;
        }
    }

    private List<SendMessageBatchRequestEntry> buildBatchMessage(List<PagamentoMessage> listaMessage){

        List<SendMessageBatchRequestEntry> entries = new ArrayList<>();

        listaMessage.forEach(item -> {
            SendMessageBatchRequestEntry entry = new SendMessageBatchRequestEntry();
            entry.setMessageBody(JsonConverter.toJson(item));
            entry.setId(UUID.randomUUID().toString());
            entry.setDelaySeconds(Integer.parseInt(this.delayMessage));
            entries.add(entry);

        });

        return entries;
    }

    private SendMessageBatchRequest buildBatchRequest(final List<SendMessageBatchRequestEntry> entries){
        final SendMessageBatchRequest batchRequest = new SendMessageBatchRequest();
        batchRequest.setEntries(entries);
        batchRequest.setQueueUrl(this.queueUrl);

        return batchRequest;
    }

}
