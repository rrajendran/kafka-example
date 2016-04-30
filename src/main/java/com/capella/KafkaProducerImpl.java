package com.capella;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * Hello world!
 */
public class KafkaProducerImpl implements KafkaProducer {
    public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(KafkaProducerImpl.class);
    private Properties properties = new Properties();
    ProducerConfig producerConfig;
    {
        try {
            properties.load(KafkaProducerImpl.class.getClassLoader().getResourceAsStream("kafka.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KafkaProducerImpl() {
        producerConfig = new ProducerConfig(properties);
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
