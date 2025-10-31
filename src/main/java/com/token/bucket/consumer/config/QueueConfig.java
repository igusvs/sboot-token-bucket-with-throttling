package com.token.bucket.consumer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;



import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableSqs
public class QueueConfig {

    @Bean
    @Primary
    public AmazonSQSAsync amazonSQSAsync() {
        return AmazonSQSAsyncClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("http://localhost:4566", "us-east-1"))
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials("accessKeyIdFake", "secretAccessKeyFake")))
                .build();
    }

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate() {

        return new QueueMessagingTemplate(amazonSQSAsync());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}