package com.token.bucket.service.strategy;

import com.token.bucket.domain.PublishType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class SqsPublishStrategyFactory {

    private final Map<PublishType, SqsPublishStrategy> strategyMap = new EnumMap<>(PublishType.class);

    public SqsPublishStrategyFactory(HotQueueStrategy hotQueueStrategy,
                                     ThrottlingQueueStrategy throttlingQueueStrategy){

        strategyMap.put(PublishType.QUEUE_HOT, hotQueueStrategy);
        strategyMap.put(PublishType.QUEUE_THROTTLING, throttlingQueueStrategy);

    }

    public SqsPublishStrategy getStrategy(PublishType type){
        SqsPublishStrategy strategy = strategyMap.get(type);

        if (strategy == null) {
            throw new IllegalArgumentException("No SQS publish strategy for type: " + type);
        }
        return strategy;
    }

}

