package com.example.consumer;


import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConsumerWithSyncOffsetCommit  extends DefaultConsumer{
    private static KafkaConsumer<String,String> consumer;

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new ShutdownThread());

        Properties config = properties();
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        consumer = new KafkaConsumer<String, String>(config);
        consumer.subscribe(Arrays.asList(TOPIC_NAME));

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                Map<TopicPartition, OffsetAndMetadata> currentOffset = new HashMap<>();

                for (ConsumerRecord<String, String> record : records) {
                    logger.info("============record:{}", record);
                    currentOffset.put(
                            new TopicPartition(record.topic(), record.partition()),
                            new OffsetAndMetadata(record.offset() + 1, null));
                    consumer.commitSync(currentOffset);
                }
            }
        }catch (WakeupException e) {
            logger.warn("============wakeup consumer============");
        }finally {
            consumer.close();
        }

    }


    static class ShutdownThread extends Thread {
        @Override
        public void run() {
            logger.info("============Shutdown hook============");
            consumer.close();
        }
    }
}
