/*
 * A simple rest service that accepts JSON data and publishes to a queue in RabbitMQ for asynchronous processing.
 */

package com.pb.enroll.rest;

import java.io.IOException;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.pb.enroll.rest.beans.User;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


/**
 *
 * @author rhea
 */
@Path("public")
public class EnrollRestService {
    
    @Context
    ResourceContext rc;
    
    private static Properties props;
    
    private static ConnectionFactory factory; 
    private static Connection connection = null;
    private static final String exchange;
    private static final String routingKey;
    
    static {
    	exchange = getProperty("broker.exchange01");
    	routingKey = getProperty("q01.routing.key");
    	
    	factory = new ConnectionFactory();
        factory.setHost(getProperty("broker.host"));
        factory.setUsername(getProperty("broker.username"));
        factory.setPassword(getProperty("broker.password"));
        
		try {
			connection = factory.newConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@QueryParam("username") String userName) {
    	System.out.println("into get ==================================================================");
        User user = new User();
        user.setMessage("Hello " + userName);
        return user;
    }    
    
    @POST
    @Path("enroll")
    @Consumes(MediaType.APPLICATION_JSON)
    public void acceptEnrollment(String user) {

    	//System.out.println("Enrolling " + user.getMessage() + " on connection " + factory);
        
        Channel channel = null;
        
		try {
			channel = connection.createChannel();
	        
	        channel.basicPublish(exchange, routingKey, null,
	        		//SerializationUtils.serialize(user.getMessage())
	        		user.getBytes()
	        );
	        
	        
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (channel != null) {
					channel.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
    
    private static String getProperty(String key) {
    	if (props == null) {
    		props = new Properties();
    		
            try {
    			props.load(com.pb.enroll.rest.EnrollRestService.class.getClassLoader().getResourceAsStream("broker.properties"));
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
