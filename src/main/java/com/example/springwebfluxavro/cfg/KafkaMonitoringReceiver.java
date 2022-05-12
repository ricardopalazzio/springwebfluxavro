package com.example.springwebfluxavro.cfg;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

@Configuration
public class KafkaMonitoringReceiver {


    @Value("${spring.kafka.bootstrap-servers}")
    private  String bootstrapserver;
    @Value("${spring.kafka.consumer.properties.schema.registry.url}")
    private  String schemaregistry;
    @Value("${spring.kafka.consumer.group-id}")
    private  String consumerid;
    @Value("${avro.topic.name}")
    private  String topicName;
    
    Map<String, Object> consumerProps = new HashMap<>();

    @PostConstruct
    public void configure() {
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapserver);
        consumerProps.put("schema.registry.url", schemaregistry);
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, consumerid);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"latest");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        consumerProps.put("specific.avro.reader", false);
    }
    

    @Bean
    public KafkaReceiver<GenericRecord,GenericRecord> kafkaSubscription(){
        return KafkaReceiver.create(
            ReceiverOptions.<GenericRecord, GenericRecord>create(consumerProps).subscription(Collections.singleton(topicName))
        );
    }
    
}
