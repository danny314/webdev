package com.acid.remote.client;


import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

import com.acid.remote2.ejb.MyName;
import com.acid.remote2.ejb.PublisherRemote;

@Named("hello")
@SessionScoped
public class Hello {
	
	@EJB(name="ejb/MyBean")
	private MyName sessionBean;

	@EJB(name="ejb/LocalMyBean")
	private MyName localBean;
	
    @EJB
    private PublisherRemote publisher;


	private String name;

    public Hello() {
    }

    public String getName() {
    	System.out.println("Remote Session bean instance = " + sessionBean);
    	System.out.println("Local Session bean instance = " + localBean);
    	System.out.println("Local publisher instance = " + publisher);
    	long now = System.currentTimeMillis();
    	Map<String,List<String>> bigMap = localBean.sayMyName(String.valueOf(Math.random()));
    	long later = System.currentTimeMillis();
    	System.out.println("Total call time = "  + (later - now));
    	//System.out.println("Map byte size = "  + ObjectSizeFetcher.getObjectSize(bigMap));
    	name = "Client bigMap size = " + bigMap.size();
    	
    	long size = 0L;
    	
    	Iterator<List<String>> iterator = bigMap.values().iterator();
    	
    	while (iterator.hasNext()) {
    		List<String> list = iterator.next(); 
        	//for (int j = 0; j < 1000; j++) {
        		size = size + list.get(0).getBytes().length;
        	//}
    	}
    	
    	System.out.println("Client bigMap size = "  + size);
    	
    	System.out.println("Publishing news...");
    	publisher.publishNews();
    	

        return name;
    }

    public void setName(String user_name) {
        this.name = user_name;
    }
}

