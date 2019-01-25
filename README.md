# UserService

How to start the UserService application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/microservice-0.0.1-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Generating coverage in Maven
1. Run `mvn clean jacoco:prepare-agent package`.  I didn't get the plugin working in the test lifecycle.

Next steps:
1. Add labels to the timers on the service methods.
2. Implement mocks for the service method unit tests instead of hitting the application.


Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`
