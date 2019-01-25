package com.example.microservice.api;

import static io.dropwizard.testing.FixtureHelpers.*;
import static org.assertj.core.api.Assertions.assertThat;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserTest {
	
	private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
	final User user = new User(1, "d", "k", "97214", "test@gmail.com");

	@Test
	public void notUser() throws Exception {
		assertThat(user).isNotEqualTo("Foo");
	}
	@Test
	public void notEqualUser() throws Exception {
		User user2 = new User(2, "a", "z", "97211", "tests@gmail.com");
		assertThat(user).isNotEqualTo(user2);
		user2 = new User(1, "a", "k", "97214", "test@gmail.com");
		assertThat(user).isNotEqualTo(user2);
		user2 = new User(1, "d", "l", "97214", "test@gmail.com");
		assertThat(user).isNotEqualTo(user2);
		user2 = new User(1, "d", "k", "97211", "test@gmail.com");
		assertThat(user).isNotEqualTo(user2);
		user2 = new User(1, "d", "k", "97214", "test1@gmail.com");
		assertThat(user).isNotEqualTo(user2);
	}
    @Test
    public void serializesToJSON() throws Exception {
    	

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/user.json"), User.class));

        assertThat(MAPPER.writeValueAsString(user)).isEqualTo(expected);
    }
    @Test
    public void deserializesFromJSON() throws Exception {
        final User user = new User(1, "d", "k", "97214", "test@gmail.com");

        assertThat(MAPPER.readValue(fixture("fixtures/user.json"), User.class))
                .isEqualTo(user);
    }

}
