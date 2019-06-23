package com.course.kafka.tutorial1;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerDemoWithCallBack {
	
	public static void main(String[] args) {
		
		final Logger logger = LoggerFactory.getLogger(ProducerDemoWithCallBack.class);
		
		String bootstrapServers = "127.0.0.1:9092";
		String topic = "firts_application";
		
		//Create Producer properties
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		
		//Create the producer
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
		
		for(int i=0;i<10;i++) {
			//Create producer record
			ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, "Hello world");
			
			//send data - asynchronous
			producer.send(record, new Callback() {
				public void onCompletion(RecordMetadata metadata, Exception exception) {
					//Executes every time  record is successfully sent or a exception is raised
					if(exception==null) {
						//The record was successfully sent
						logger.info("Received new metadata \n"
								+ "Topic: "+ metadata.topic() + "\n"
								+ "Partition: "+ metadata.partition()+ "\n"
								+ "Offset: "+ metadata.offset()+"\n"
								+ "Timestamp: "+ metadata.timestamp());
					}
					else {
						logger.error("Error while producing: ",exception);
					}
				}
			});
		}
		//Flush data
		producer.flush();
		
		//Flush and close
		producer.close();
	}

}
