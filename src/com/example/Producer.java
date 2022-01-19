package com.example;

import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;

public class Producer {

    public static void main(String[] args){

	//String certPath="/Users/lilim/temp/tlsclientkafka";
	String certPath="..";

	if (args.length == 0) {
		System.out.println("Please set the url of external load balancer as 1st parameter.");
		return; 
	}

        Properties properties = new Properties();
        properties.put("bootstrap.servers", args[0] + ":9093");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	properties.put("security.protocol", "SSL");
	properties.put("ssl.truststore.location", certPath + "/truststore.p12");
	properties.put("ssl.truststore.password",  "welcome1");
        properties.put("ssl.truststore.type", "PKCS12");
        properties.put("ssl.keystore.location", certPath + "/keystore.p12");
        properties.put("ssl.keystore.password", "welcome1");
        properties.put("ssl.keystore.type" , "PKCS12");

        KafkaProducer kafkaProducer = new KafkaProducer(properties);


        try{

            for(int i = 0; i < 3; i++){
                System.out.println(i);
                kafkaProducer.send(new ProducerRecord("test", Integer.toString(i), "test message - " + i ));
	    	kafkaProducer.flush();
	    }
            System.out.println("closing"); 
	    kafkaProducer.close();
            System.out.println("closed");
        
	}catch (Exception e){
            e.printStackTrace();
        }finally {
            //kafkaProducer.close();
        }
	
	System.out.println("end");
    }
}
