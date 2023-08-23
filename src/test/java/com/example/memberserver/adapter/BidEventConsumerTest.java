package com.example.memberserver.adapter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@EmbeddedKafka(partitions = 1,
topics = "${kafka.topic.bid}",
brokerProperties = {
        "listeners=PLAINTEXT://localhost:9092",
        "port=9092"
})
@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext
class BidEventConsumerTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

//    @Autowired
//    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private BidEventConsumer bidEventConsumer;

    @Test
    public void testConsumer() throws Exception {
        //given
        String message = "{\"memberId\":1, \"bid\":1000}";



        //when

        //then
    }

}