package com.example.demo;

import com.example.demo.domain.UserAuthenticationToken;
import com.example.demo.dto.UserRequestDTO;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest
public class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	public static void loginTestUser(UserService userService, int num) {
		UserRequestDTO userRequestDTO = new UserRequestDTO();
		userRequestDTO.setUsername("username" + num);
		userRequestDTO.setProfileName(userRequestDTO.getUsername() + "name");
		userRequestDTO.setPassword("password");
		UserAuthenticationToken user = new UserAuthenticationToken(userService.save(userRequestDTO));

		Authentication authToken = new UsernamePasswordAuthenticationToken(
				user,
				null,
				user.getAuthorities()
		);
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}

}
