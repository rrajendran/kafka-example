package com.capella.kafka.example;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.text.SimpleDateFormat;

/**
 * Hello world!
 */
public class KafkaProducerImpl implements KafkaProducer {
    public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(KafkaProducerImpl.class);
    private ProducerConfig producerConfig;


    public KafkaProducerImpl() {
        producerConfig = new ProducerConfig(PropertiesHelper.getInstance().getProperties());
    }

    public void send(String message) {
        LOGGER.info("Sending message = " + message);

        kafka.javaapi.producer.Producer<String, String> producer = new kafka.javaapi.producer.Producer<String, String>(producerConfig);
        SimpleDateFormat sdf = new SimpleDateFormat();
        KeyedMessage<String, String> keyedMessage = new KeyedMessage<String, String>("test", message);
        producer.send(keyedMessage);
        producer.close();
    }
}
