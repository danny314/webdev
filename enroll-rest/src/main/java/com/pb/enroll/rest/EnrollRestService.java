/*
 * A simple rest service that accepts JSON data and publishes to a queue in RabbitMQ for asynchronous processing.
 */

package com.pb.enroll.rest;

import com.pb.enroll.rest.beans.User;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.SerializationUtils;


/**
 *
 * @author rhea
 */
@ApplicationPath("/*")
@Path("helloworld")
public class EnrollRestService extends Application {
    
    @Context
    ResourceContext rc;
    
    private Properties props;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@QueryParam("username") String userName) {
        User user = new User();
        user.setMessage("Hello " + userName);
        return user;
    }    
    
    @POST
    @Path("enroll")
    @Consumes(MediaType.APPLICATION_JSON)
    public void acceptEnrollment(User user) {
        System.out.println("Enrolling " + user.getMessage());
        
        ConnectionFactory factory = new ConnectionFactory();
        
        factory.setHost(getProperty("broker.host"));
        factory.setUsername(getProperty("broker.username"));
        factory.setPassword(getProperty("broker.password"));
        
        Connection connection = null;
        Channel channel = null;
        
		try {
			connection = factory.newConnection();
	        
			channel = connection.createChannel();
	        
	        channel.basicPublish(
	        		getProperty("broker.exchange01"),
	        		getProperty("q01.routing.key"),
	        		null,
	        		//SerializationUtils.serialize(user.getMessage())
	        		getRegistrationBytes(user.getMessage())
	        );
	        
	        
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
    			props.load(getClass().getClassLoader().getResourceAsStream("broker.properties"));
    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}
            
    	}
    	return props.getProperty(key);
    }
    
/*    private JsonObject getRandomRegistration() {
    	JsonObject model = Json.createObjectBuilder()
    		.add("firstName", "Duke")
    		.build();
    	
    	return model;
    }
*/
    private byte[] getRegistrationBytes(String message) {
    	String registrationJson = "{ \"name\":\"" + message + "\" }";
    	return registrationJson.getBytes();
    }    

}
