package com.capella;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by ramesh on 30/04/2016.
 */
public class KafkaServerBaseTest {
    static ZookeeperEmbeddedServer zookeeperEmbeddedServer;
    static KafkaEmbeddedServer kafkaEmbeddedServer;

    @BeforeClass
    public static void init() throws IOException {
        zookeeperEmbeddedServer = new ZookeeperEmbeddedServer(2181);
        List<Integer> kafkaPorts = new ArrayList<Integer>();
        // -1 for any available port
        kafkaPorts.add(9092);
        kafkaEmbeddedServer = new KafkaEmbeddedServer(zookeeperEmbeddedServer.getConnection(), new Properties(), kafkaPorts);
        zookeeperEmbeddedServer.startup();
        System.out.println("### Embedded Zookeeper connection: " + zookeeperEmbeddedServer.getConnection());
        kafkaEmbeddedServer.startup();
        System.out.println("### Embedded Kafka cluster broker list: " + kafkaEmbeddedServer.getBrokerList());
    }

    @AfterClass
    public static void shutdown() {
        if (kafkaEmbeddedServer != null)
            kafkaEmbeddedServer.shutdown();
        if (zookeeperEmbeddedServer != null)
            zookeeperEmbeddedServer.shutdown();
    }
}
