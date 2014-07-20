/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pb.enroll.rest;

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
    @Produces("text/plain")
    public String getUser(@QueryParam("username") String userName) {
        return "Hello" + userName;
    }    
    
    @POST
    @Consumes("text/plain")
    public void acceptEnrollment(String message) {
        // Store the message
    }

}
