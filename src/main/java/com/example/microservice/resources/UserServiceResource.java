package com.example.microservice.resources;

import com.example.microservice.api.User;
import com.example.microservice.metrics.Metrics;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.annotation.Timed;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.jetty.http.HttpParser.RequestHandler;

import java.util.concurrent.atomic.AtomicLong;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserServiceResource {

    private final AtomicLong counter;
    private final Timer responses = Metrics.getMetricRegistry().timer(MetricRegistry.name(RequestHandler.class, "responses"));
	private HashMap<Long, User> allUsers;
    //public UserServiceResource(String defaultFirstName, String defaultLastName, String defaultZipCode, String defaultEmail) 
    public UserServiceResource(){

        this.counter = new AtomicLong();
        this.allUsers = new HashMap<Long,User>();
        User user = new User(1, "d", "k", "97214", "test@gmail.com");
        allUsers.put(counter.incrementAndGet(), user);
    }

    @GET
    @Timed
    @Path("{id}")
    public User getUser(@PathParam("id") final long id) {
    	final Timer.Context context = responses.time();//1
    	try {
        return this.allUsers.get(id);
    	}finally {
    		context.stop();
    	}
    }
    
    @GET
    @Timed
    public List<User> getAllUsers() {
    	final Timer.Context context = responses.time();//1
    	try {
    		 
    	     return new ArrayList<User>(this.allUsers.values());
    	}finally {
    		context.stop();
    	}

    }
    
    @PUT
    @Timed
    @Path("{id}")
    public User updateUser(@NotNull @Valid final User user, @PathParam("id") final long id) {
    	final Timer.Context context = responses.time();//1
    	try {
    	if(user.getId() == 0 || user.getId() == id) {
    		User newUser = new User(id, user.getFirstName(), user.getLastName(), user.getZipCode(), user.getEmail());
    	    this.allUsers.replace(id, newUser);
    	    return newUser;
    	}
        return null;
    	}finally {
    		context.stop();
    	}
    }
    
    @PUT
    @Timed
    public List<User> updateUsers(List<User> users) {
    	final Timer.Context context = responses.time();//1
    	try {
    	List<User> returnList = new ArrayList<User>();
    	for(User user : users){
    		long id = user.getId();
    		User updatedUser = updateUser(user, id);
    		returnList.add(updatedUser);
    	}
        return returnList;
    	}finally {
    		context.stop();
    	}
    }
    
    @POST
    @Timed
    public User createUser(User user) {
    	final Timer.Context context = responses.time();
    	try {
    	long count = counter.incrementAndGet();
    	User newUser = new User(count, user.getFirstName(), user.getLastName(), user.getZipCode(), user.getEmail());
    	this.allUsers.put(count, newUser);
    	return newUser;
    	}finally {
    		context.stop();
    	}
    }
    
    @POST
    @Path("/create")
    @Timed
    public List<User> createUsers(List<User> users) {
    	final Timer.Context context = responses.time();
        try {
    	List<User> returnList = new ArrayList<User>();
    	for (User user : users) {
    		long count = counter.incrementAndGet();
    		User newUser = new User(count, user.getFirstName(), user.getLastName(), user.getZipCode(), user.getEmail());
        	this.allUsers.put(count, newUser);
        	returnList.add(newUser);
    	}
    	return returnList;
        }finally {
        	context.stop();
        }
    }
    
    @DELETE
    @Timed
    @Path("{id}")
    public String deleteUser(@PathParam("id") final long id) {
    	final Timer.Context context = responses.time();
    	try {
        this.allUsers.remove(id);
        return "User: " +id + " deleted."; 
    	}finally {
    		context.stop();
    	}
    }
    
    @POST
    @Timed
    @Path("/delete")
    public String deleteUsers(List<Long> users) {
    	final Timer.Context context = responses.time();
    	try {
    	String returnValue = "Users: ";
    	for (long id: users) {
         this.allUsers.remove(id);
         returnValue += id + " ";
    	}
        return returnValue +"deleted.";
    	}finally {
    		context.stop();
    	}
    }
}
