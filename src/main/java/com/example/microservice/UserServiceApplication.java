package com.example.microservice;

import com.example.microservice.metrics.Metrics;
import com.example.microservice.resources.UserServiceResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;



public class UserServiceApplication extends Application<UserServiceConfiguration> {

    public static void main(final String[] args) throws Exception {
        new UserServiceApplication().run(args);
    }

    @Override
    public String getName() {
        return "UserService";
    }

    @Override
    public void initialize(final Bootstrap<UserServiceConfiguration> bootstrap) {
        Metrics.initialize();
    }

    @Override
    public void run(final UserServiceConfiguration configuration,
                    final Environment environment) {
    	final UserServiceResource resource = new UserServiceResource(
    	    );
    	    environment.jersey().register(resource);
    }

}
