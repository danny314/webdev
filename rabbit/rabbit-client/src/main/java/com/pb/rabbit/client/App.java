package com.pb.rabbit.client;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.17.0.2");
        
        Connection connection = null;
        
		try {
			connection = factory.newConnection();
	        Channel channel = connection.createChannel();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
