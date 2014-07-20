/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pb.enroll.rest;

import com.pb.enroll.rest.beans.User;
import java.util.HashMap;
import java.util.Map;
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

/**
 *
 * @author rhea
 */
@ApplicationPath("/*")
@Path("helloworld")
public class EnrollRestService extends Application {
    
    @Context
    ResourceContext rc;
        
    
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
    }

}
