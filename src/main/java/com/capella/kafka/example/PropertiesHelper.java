package com.capella.kafka.example;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by ramesh on 30/04/2016.
 */
public class PropertiesHelper {
    private Properties properties = new Properties();

    {
        try {
            properties.load(KafkaProducerImpl.class.getClassLoader().getResourceAsStream("kafka.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static PropertiesHelper instance;

    public static PropertiesHelper getInstance() {
        return instance == null ? instance = new PropertiesHelper() : instance;
    }

    public Properties getProperties() {
        return this.properties;
    }

    public String getProperty(String name) {
        return (String) this.properties.get(name);
    }
}
