/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acid.remote2.ejb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author martin
 */
@Stateless
@Remote(MyName.class)
public class MyNameBean implements MyName {

    public Map<String,List<String>> sayMyName(String name) {
    	String message = "Hello " + name;
    	System.out.println("Computing message = " + message);
    	
    	Map<String,List<String>> bigMap = new HashMap<>();
    	List<String> strList = null;
    	for (int i = 0; i < 100; i++) {
    		strList = new ArrayList<>();
        	for (int j = 0; j < 1000; j++) {
        		strList.add(RandomStringUtils.randomAlphanumeric(25));
        	}
    		bigMap.put(RandomStringUtils.randomAlphanumeric(25), strList);
    	}
    	
    	//System.out.println("Server bigMap = "  + bigMap);
    	return bigMap;
    }

}
