package com.example.microservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.dropwizard.testing.junit.DropwizardAppRule;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Rule;
import org.junit.Test;

import com.example.microservice.api.User;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceIntegrationTest {
    @Rule
    public final DropwizardAppRule<UserServiceConfiguration> RULE =
        new DropwizardAppRule<UserServiceConfiguration>(UserServiceApplication.class);

    private User buildUserfromMap(Map um) {
    	        User user = new User(
        		(int) um.get("id"),
        		um.get("firstName").toString(),
        		um.get("lastName").toString(),
        		um.get("zipCode").toString(),
        		um.get("email").toString()
                );
    	       return user;
    }
    
    @Test
    public void runServerTest() {
    	User user = new User(1, "d", "k", "97214", "test@gmail.com");
        Client client = new JerseyClientBuilder().build();
        User result = client.target(
            String.format("http://localhost:%d/users/1", RULE.getLocalPort())
        ).request().get(User.class);
        assertThat(result).isEqualTo(user);
    }
    
    @Test
    public void getAllUsersTest() {
    	User user = new User(1, "d", "k", "97214", "test@gmail.com");
    	List<User> userList = new ArrayList<User>();
    	userList.add(user);
        Client client = new JerseyClientBuilder().build();
        List result = client.target(
            String.format("http://localhost:%d/users", RULE.getLocalPort())
        ).request().get(List.class);
        HashMap um = (HashMap) result.get(0);
        User returnUser = buildUserfromMap(um);
        assertThat(user).isEqualTo(returnUser);
    }
    
    @Test
    public void createUserTest() {
    	User user = new User(2, "d", "k", "97214", "test@gmail.com");
    	List<User> userList = new ArrayList<User>();
    	userList.add(user);
        Client client = new JerseyClientBuilder().build();
        Response response = client.target(
            String.format("http://localhost:%d/users", RULE.getLocalPort())
        ).request().post(Entity.entity(user, MediaType.APPLICATION_JSON));
        User returnUser = response.readEntity(User.class);
        assertThat(user).isEqualTo(returnUser);
    }
    
    @Test
    public void updateUserTest() {
    	User user = new User(1, "da", "ka", "97211", "test11@gmail.com");
    	List<User> userList = new ArrayList<User>();
    	userList.add(user);
        Client client = new JerseyClientBuilder().build();
        Response response = client.target(
            String.format("http://localhost:%d/users/1", RULE.getLocalPort())
        ).request().put(Entity.entity(user, MediaType.APPLICATION_JSON));
        User returnUser = response.readEntity(User.class);
        assertThat(user).isEqualTo(returnUser);
    }
    
    @Test
    public void updateWrongUserTest() {
    	User user = new User(2, "da", "ka", "97211", "test11@gmail.com");
    	List<User> userList = new ArrayList<User>();
    	userList.add(user);
        Client client = new JerseyClientBuilder().build();
        Response response = client.target(
            String.format("http://localhost:%d/users/1", RULE.getLocalPort())
        ).request().put(Entity.entity(user, MediaType.APPLICATION_JSON));
        User returnUser = response.readEntity(User.class);
        assertThat(returnUser).isNull();
    }
    
    public void updateUser0Test() {
    	User testUser = new User(2, "da", "ka", "97211", "test11@gmail.com");
    	User user = new User(0, "da", "ka", "97211", "test11@gmail.com");
    	List<User> userList = new ArrayList<User>();
    	userList.add(user);
        Client client = new JerseyClientBuilder().build();
        Response response = client.target(
            String.format("http://localhost:%d/users/1", RULE.getLocalPort())
        ).request().put(Entity.entity(user, MediaType.APPLICATION_JSON));
        User returnUser = response.readEntity(User.class);
        assertThat(returnUser).isEqualTo(testUser);
    }
    
    @Test
    public void deleteUserTest() {
    	Client client = new JerseyClientBuilder().build();
        String result = client.target(
                String.format("http://localhost:%d/users/1", RULE.getLocalPort())
            ).request().delete(String.class);
            assertThat("User: 1 deleted.").isEqualTo(result);
    }
    
    @Test
    public void createUsersTest() {
    	User user1 = new User(2, "d", "k", "97214", "test@gmail.com");
    	User user2 = new User(2, "d", "k", "97211", "test@gmail.com");
    	List<User> userList = new ArrayList<User>();
    	userList.add(user1);
    	userList.add(user2);
        Client client = new JerseyClientBuilder().build();
        Response response = client.target(
            String.format("http://localhost:%d/users/create", RULE.getLocalPort())
        ).request().post(Entity.entity(userList, MediaType.APPLICATION_JSON));
        List responseList = response.readEntity(List.class);

        assertThat(userList.size()).isEqualTo(responseList.size());
    }
    
    @Test
    public void updateUsersTest() {
    	User user = new User(1, "da", "ka", "97211", "test11@gmail.com");
        List<User> userList = new ArrayList<User>();
        List<User> returnList = new ArrayList<User>();
    	userList.add(user);
    	userList.add(user);
        Client client = new JerseyClientBuilder().build();
    	Response response = client.target(
                String.format("http://localhost:%d/users/create", RULE.getLocalPort())
            ).request().post(Entity.entity(userList, MediaType.APPLICATION_JSON));
    	List responseList = response.readEntity(List.class);
    	userList.clear();
    	
        for(Object obj : responseList) {
        	HashMap um = (HashMap) obj;
        	User tempUser = new User(
            		(int) um.get("id"),
            		um.get("firstName").toString(),
            		um.get("lastName").toString(),
            		um.get("zipCode").toString(),
            		"newemail@address.com"
                    );
        	userList.add(tempUser);
        }
    	response = client.target(
            String.format("http://localhost:%d/users/", RULE.getLocalPort())
        ).request().put(Entity.entity(userList, MediaType.APPLICATION_JSON));
    	responseList = response.readEntity(List.class);
    	for(Object obj : responseList) {
    		HashMap um = (HashMap) obj;
    		User tempUser = buildUserfromMap(um);
    		returnList.add(tempUser);
    	}
        assertThat(userList).isEqualTo(returnList);
    }
    
    @Test
    public void deleteUsersTest() {
        List<Long> userIds = new ArrayList<Long>();
    	User user1 = new User(2, "d", "k", "97214", "test@gmail.com");
    	User user2 = new User(2, "d", "k", "97211", "test@gmail.com");
    	List<User> userList = new ArrayList<User>();
    	userList.add(user1);
    	userList.add(user2);
        Client client = new JerseyClientBuilder().build();
        Response response = client.target(
            String.format("http://localhost:%d/users/create", RULE.getLocalPort())
        ).request().post(Entity.entity(userList, MediaType.APPLICATION_JSON));
        userIds.add(new Long(1));
        userIds.add(new Long(2));
        userIds.add(new Long(3));
        
        response = client.target(
                String.format("http://localhost:%d/users/delete", RULE.getLocalPort())
                ).request().post(Entity.entity(userIds, MediaType.APPLICATION_JSON));
        String result = response.readEntity(String.class);
        assertThat("Users: 1 2 3 deleted.").isEqualTo(result);
    }
}
