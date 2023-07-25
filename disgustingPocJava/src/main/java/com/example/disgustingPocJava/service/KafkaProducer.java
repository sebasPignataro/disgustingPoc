package com.example.disgustingPocJava.service;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.disgustingPocJava.domain.DisgustingEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaProducer {

	@Autowired
	private KafkaTemplate<String, GenericRecord> kafkaTemplate;

	public void sendMessage(String topic, DisgustingEvent disgustingEvent) {
		GenericRecord genericRecord = buildGenericRecord(disgustingEvent, DisgustingEvent.SCHEMA$);
		log.info("Produce event '{}' and write to topic", disgustingEvent.toString(), topic);
		kafkaTemplate.send(topic, disgustingEvent.getField1(), genericRecord);
	}

	public GenericRecord buildGenericRecord(DisgustingEvent disgustingEvent, Schema emailSchema) {

		GenericRecordBuilder disgustingEventBuilder = new GenericRecordBuilder(emailSchema);
		disgustingEventBuilder.set("field1", disgustingEvent.getField1());
		disgustingEventBuilder.set("field2", disgustingEvent.getField2());
		return disgustingEventBuilder.build();
	}
}
