package com.bootcamp.java.report.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.topic.productclient.name}")
    private String topicProductclient;

    @Value("${spring.kafka.topic-transaction.name}")
    private String topicTransaction;

    @Bean
    public NewTopic productClientTopic(){
        return TopicBuilder.name(topicProductclient)
                .build();
    }

    @Bean
    public NewTopic transactionTopic(){
        return TopicBuilder.name(topicTransaction)
                .build();
    }
}
