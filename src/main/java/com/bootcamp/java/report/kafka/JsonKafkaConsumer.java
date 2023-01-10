package com.bootcamp.java.report.kafka;

import com.bootcamp.java.kafka.ProductClientDTO;
import com.bootcamp.java.kafka.TransactionDTO;
import com.bootcamp.java.report.converter.ProductClientConvert;
import com.bootcamp.java.report.converter.TransactionConvert;
import com.bootcamp.java.report.service.productClient.ProductClientService;
import com.bootcamp.java.report.service.transaction.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class JsonKafkaConsumer {

    @Autowired
    ProductClientService productClientService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    ProductClientConvert productClientConvert;

    @Autowired
    TransactionConvert transactionConvert;

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaConsumer.class);

    @KafkaListener(topics = "${spring.kafka.topic.productclient.name:my_topic_productclient}",
            groupId = "${spring.kafka.consumer.group-id:myGroup}"
    )
    public void consumeProductClient(ProductClientDTO productClientDTO){
        LOGGER.info(String.format("consumeProductClientDTO message recieved -> %s", productClientDTO.toString()));
        productClientService.create(productClientConvert.KafkaDTOToDTO(productClientDTO));
        LOGGER.info(String.format("consumeProductClientDTO REGISTERED"));
    }

    @KafkaListener(topics = "${spring.kafka.topic-transaction.name:my_topic_transaction}",
            groupId = "${spring.kafka.consumer.group-id:myGroup}"
    )
    public void consumeTransaction(TransactionDTO transactionDTO){
        LOGGER.info(String.format("consumeTransactionDTO message recieved -> %s", transactionDTO.toString()));
        transactionService.register(transactionConvert.KafkaDTOtoEntity(transactionDTO));
        LOGGER.info(String.format("consumeTransactionDTO REGISTERED"));
    }

}
