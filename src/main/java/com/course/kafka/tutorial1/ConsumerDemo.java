package com.course.kafka.tutorial1;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerDemo {
	
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(ConsumerDemo.class);
		
		String bootstrapServers = "127.0.0.1:9092";
		String groupId = "my-first-application";
		String topic = "first_application";
		
//		Create consumer config
		Properties properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//		It could be of three type 
//		earliest- If you want to read data from start
//		latest - If you want to read the latest message
//		none will throw an error if there is no offsets being saved
		properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		
//		create consumer
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
		
//		Subscribe our consumer to new topic(s)
		consumer.subscribe(Arrays.asList(topic));
		
//		poll for new data
		while(true) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(300));
			for(ConsumerRecord< String, String> record :records) {
				logger.info("Key: "+record.key()+", Value: "+record.value());
				logger.info("Partition: "+record.partition()+", Offsets"+record.partition());
			}
		}
	}
	

}
