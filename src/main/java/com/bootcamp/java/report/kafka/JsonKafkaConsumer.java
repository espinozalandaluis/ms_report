package com.bootcamp.java.report.kafka;

import com.bootcamp.java.kafka.ProductClientDTO;
import com.bootcamp.java.kafka.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class JsonKafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaConsumer.class);

    @KafkaListener(topics = "${spring.kafka.topic-transaction.name}",
            groupId = "${spring.kafka.consumer.group-id}"
            )
    public void consume(User user){
        LOGGER.info(String.format("Json message recieved -> %s", user.toString()));
    }

    @KafkaListener(topics = "${spring.kafka.topic.productclient.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeProductClient(ProductClientDTO productClientDTO){
        LOGGER.info(String.format("consumeProductClient Json message recieved -> %s", productClientDTO.toString()));
    }

}
