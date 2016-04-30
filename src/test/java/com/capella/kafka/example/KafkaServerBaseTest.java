package com.capella.kafka.example;

import com.capella.kafka.embedded.KafkaEmbeddedServer;
import com.capella.kafka.embedded.ZookeeperEmbeddedServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by ramesh on 30/04/2016.
 */
public class KafkaServerBaseTest {
    public static final Logger LOGGER = getLogger(KafkaServerBaseTest.class);
    public static final int KAFKA_PORT_NUMBER = 9092;
    public static final int ZOOKEEPER_PORT_NUMBER = 2181;
    static ZookeeperEmbeddedServer zookeeperEmbeddedServer;
    static KafkaEmbeddedServer kafkaEmbeddedServer;

    @BeforeClass
    public static void init() throws IOException {
        zookeeperEmbeddedServer = new ZookeeperEmbeddedServer(ZOOKEEPER_PORT_NUMBER);
        List<Integer> kafkaPorts = new ArrayList<Integer>();
        // -1 for any available port
        kafkaPorts.add(KAFKA_PORT_NUMBER);
        kafkaEmbeddedServer = new KafkaEmbeddedServer(zookeeperEmbeddedServer.getConnection(), new Properties(), kafkaPorts);
        zookeeperEmbeddedServer.startup();
        LOGGER.info("### Embedded Zookeeper connection: " + zookeeperEmbeddedServer.getConnection());
        kafkaEmbeddedServer.startup();
        LOGGER.info("### Embedded Kafka cluster broker list: " + kafkaEmbeddedServer.getBrokerList());
    }

    @AfterClass
    public static void shutdown() {
        if (kafkaEmbeddedServer != null)
            kafkaEmbeddedServer.shutdown();
        if (zookeeperEmbeddedServer != null)
            zookeeperEmbeddedServer.shutdown();
    }
}
