package com.example.microservice.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class User {

    private long id;
    private String firstName;
	private String lastName;
    private String zipCode;
    private String email;

    public User() {
        // Jackson deserialization
    }

    public User(long id, String firstName, String lastName, String zipCode, String email) {
    	this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.zipCode = zipCode;
        this.email = email;
    }

    @JsonProperty
    public String getFirstName() {
		return firstName;
	}

    @JsonProperty
	public String getLastName() {
		return lastName;
	}

    @JsonProperty
	public String getZipCode() {
		return zipCode;
	}

    @JsonProperty
	public String getEmail() {
		return email;
	}
    
    @JsonProperty
	public long getId() {
		return id;
	}
    
    @Override
    public boolean equals(Object o) {
    	if(!(o instanceof User)){
    		return false;
    	}
    	User tu = (User) o;
    	if(tu.getId() == id && tu.getFirstName().equals(firstName) && tu.getLastName().equals(lastName) && tu.getZipCode().equals(zipCode) && tu.getEmail().equals(email)) {
    		return true;
    	}
    	return false;
    }
}