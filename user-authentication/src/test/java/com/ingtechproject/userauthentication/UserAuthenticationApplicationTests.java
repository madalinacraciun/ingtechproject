package com.ingtechproject.userauthentication;

import com.ingtechproject.userauthentication.entities.User;
import com.ingtechproject.userauthentication.repositories.UserRepository;
import com.ingtechproject.userauthentication.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserAuthenticationApplicationTests {

	@MockBean
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Test
	public void getUsersTest() {
		when(userRepository.findAll())
				.thenReturn(Stream.of(new User(1,"username1","password1","username1@gmail.com",22, "Romania"), new User(2, "username2", "password2", "username2@gamil.com", 24, "Spain"), new User(3, "username3", "password3", "username3@gmail.com", 31, "Italy")).collect(Collectors.toList()));
		assertEquals(3, userService.getUsers().size());
	}

	@Test
	public void getUserByUsernameTest() {
		String username = "username1";
		User user = new User(1,"username1","password1","username1@gmail.com",22, "Romania");
		when(userRepository.findByUsername(username)).thenReturn(user);
		assertEquals(user, userRepository.findByUsername(username));
	}

	@Test
	public void saveUserTest() {
		User user = new User(4, "username4", "password4", "username4@gmail.com", 16, "Portugal");
		when(userRepository.save(user)).thenReturn(user);
		assertEquals(user, userService.saveUser(user));
	}

}
