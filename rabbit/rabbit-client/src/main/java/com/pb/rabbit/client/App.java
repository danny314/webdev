package com.pb.rabbit.client;

import java.io.IOException;
import java.util.Properties;

import javax.json.Json;
import javax.json.JsonObject;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * Client to publish messages to an exchange on a remote rabbit mq broker. 
 */
public class App 
{
    Properties props;

    public static void main( String[] args )
    {
        ConnectionFactory factory = new ConnectionFactory();
        
        App app = new App();
        
        factory.setHost(app.getProperty("broker.host"));
        factory.setUsername(app.getProperty("broker.username"));
        factory.setPassword(app.getProperty("broker.password"));
        
        Connection connection = null;
        Channel channel = null;
        
		try {
			connection = factory.newConnection();
	        
			channel = connection.createChannel();
	        
			for (int i = 0; i < 5; i++) {
		        channel.basicPublish(
		        		app.getProperty("broker.exchange01"),
		        		app.getProperty("q01.routing.key"),
		        		null, 
		        		app.getRandomRegistrationBytes()
		        );
			}
	        
	        
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (channel != null) {
					channel.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
    }
    
    private String getProperty(String key) {
    	if (props == null) {
    		props = new Properties();
    		
            try {
    			props.load(App.class.getClassLoader().getResourceAsStream("broker.properties"));
    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}
            
    	}
    	return props.getProperty(key);
    }
    
    private JsonObject getRandomRegistration() {
    	JsonObject model = Json.createObjectBuilder()
    		.add("firstName", "Duke")
    		.build();
    	
    	return model;
    }

    private byte[] getRandomRegistrationBytes() {
    	String registrationJson = "{ \"name\":\"" + RandomStringUtils.randomAlphabetic(8) + "\" }";
    	return registrationJson.getBytes();
    }
    
}
