package com.course.kafka.tutorial1;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class Producerdemo {
	
	public static void main(String[] args) {
		
		String bootstrapServers = "localhost:9092";
		String topic = "firts_application";
		
		//Create Producer properties
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		
		//Create the producer
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
		
		//Create producer record
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, "Hello world");
		
		//send data - asynchronous
		producer.send(record);
		
		//Flush data
		producer.flush();
		
		//Flush and close
		producer.close();
	}

}
