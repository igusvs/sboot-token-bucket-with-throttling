package com.token.bucket.service.strategy;

import com.token.bucket.domain.PagamentoMessage;

import java.util.List;

public interface SqsPublishStrategy {

    void publish(List<PagamentoMessage> payload);
}
