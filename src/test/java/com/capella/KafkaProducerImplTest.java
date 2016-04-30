package com.capella;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.io.IOException;


/**
 * Unit test for simple KafkaProducerImpl.
 */
public class KafkaProducerImplTest extends KafkaServerBaseTest {
    private KafkaProducer kafkaProducer = new KafkaProducerImpl();

    @Test
    public void test() throws IOException, InterruptedException {
        kafkaProducer.send(RandomStringUtils.randomAlphanumeric(20));


    }

}
