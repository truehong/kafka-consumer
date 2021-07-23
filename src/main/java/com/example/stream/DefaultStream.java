package com.example.stream;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;

public class DefaultStream {
    final static String APPLICATION_NAME = "test";
    final static String BOOTSTRAP_SERVERS = "15.164.24.198:9092";
    final static String STREAM_LOG = "test-group";
    final static String STREAM_LOG_COPY = "stream_log_copy";
    private static Properties props;

    static final Properties properties() {
        props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_NAME);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        return props;
    }
}
