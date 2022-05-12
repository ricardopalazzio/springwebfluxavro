package com.example.springwebfluxavro.web.handler;

import org.apache.avro.generic.GenericRecord;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.kafka.receiver.KafkaReceiver;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import javax.annotation.PostConstruct;

import com.example.springwebfluxavro.MonitoringRecord;


/**
 * MonitorinngHandler
 */
@Service
public class MonitoringHandler {

    private final KafkaReceiver<GenericRecord,GenericRecord> receiver;
    private Flux<MonitoringRecord> stream;

    public MonitoringHandler(KafkaReceiver<GenericRecord, GenericRecord> receiver) {
        this.receiver = receiver;
    }

    @PostConstruct
    public void  connectOnKafkaReceiver() {
        stream = receiver.receive()
        .publish()
        .autoConnect(1)
        .map((t) -> MonitoringRecord.fromAvro(t.value()));
    }
    
    public Mono<ServerResponse> stream(ServerRequest request) {
        return ok().contentType(MediaType.APPLICATION_NDJSON).body(
        stream.filter(t -> t.getEquipment().equals(request.pathVariable("id")))
        ,MonitoringRecord.class);
    }
}