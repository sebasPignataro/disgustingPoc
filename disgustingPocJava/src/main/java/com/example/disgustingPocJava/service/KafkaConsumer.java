package com.example.disgustingPocJava.service;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.disgustingPocJava.domain.DisgustingEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaConsumer {

	@KafkaListener(topics = Constants.TOPIC, groupId = Constants.CONSUMER_GROUP )
	public void consumeDisgustingEvent(ConsumerRecord<String, GenericRecord> consumerRecord) {
		log.info("Consuming from topic '{}' event: '{}'", Constants.TOPIC, consumerRecord.value());
		DisgustingEvent disgustingEvent = mapRecordToObject(consumerRecord.value(), new DisgustingEvent());
		log.info("Received event '{}'", disgustingEvent.toString());
	}

	/**
	 * Avro Reflection
	 */
	public <T> T mapRecordToObject(GenericRecord record, T object) {
		record.getSchema().getFields()
				.forEach(d -> PropertyAccessorFactory.forDirectFieldAccess(object).setPropertyValue(d.name(),
						record.get(d.name()) == null ? record.get(d.name()) : record.get(d.name()).toString()));
		return object;
	}
}
