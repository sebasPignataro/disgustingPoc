package com.example.disgustingPocJava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.disgustingPocJava.domain.DisgustingEvent;
import com.example.disgustingPocJava.service.Constants;
import com.example.disgustingPocJava.service.KafkaProducer;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class DisgustingPocJavaApplication {

	@Autowired
	private KafkaProducer kafkaProducer;
	
	public static void main(String[] args) {
		SpringApplication.run(DisgustingPocJavaApplication.class, args);
	}
	
	@Scheduled(initialDelay = 20000, fixedRate = 20000)
	public void schedulledTest() {
		
		DisgustingEvent disgustingEvent = new DisgustingEvent();
		disgustingEvent.setField1("value1");
		disgustingEvent.setField2("value2");
		
		log.info("Scheduled Test, will produce event '{}'", disgustingEvent);
		
		kafkaProducer.sendMessage(Constants.TOPIC, disgustingEvent);
		
	}

}
