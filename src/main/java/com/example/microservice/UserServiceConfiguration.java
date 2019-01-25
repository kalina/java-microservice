package com.example.microservice;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.*;

public class UserServiceConfiguration extends Configuration {

    @NotEmpty
    private String defaultFirstName = "Stranger";
    
    @NotEmpty
    private String defaultLastName = "Still";
    
    @NotEmpty
    private String defaultZipCode = "97214";
    
    @NotEmpty
    private String defaultEmail = "strange@gmail.com";

    @JsonProperty
    public String getDefaultFirstName() {
        return defaultFirstName;
    }

    @JsonProperty
    public void setDefaultFirstName(String defaultFirstName) {
        this.defaultFirstName = defaultFirstName;
    }
    
    @JsonProperty
    public String getDefaultLastName() {
		return defaultLastName;
	}
    
    @JsonProperty
	public void setDefaultLastName(String defaultLastName) {
		this.defaultLastName = defaultLastName;
	}
    
    @JsonProperty
    public String getDefaultZipCode() {
		return defaultZipCode;
	}
    
    @JsonProperty
	public void setDefaultZipCode(String defaultZipCode) {
		this.defaultZipCode = defaultZipCode;
	}
    
    @JsonProperty
	public String getDefaultEmail() {
		return defaultEmail;
	}

    @JsonProperty
	public void setDefaultEmail(String defaultEmail) {
		this.defaultEmail = defaultEmail;
	}
}
