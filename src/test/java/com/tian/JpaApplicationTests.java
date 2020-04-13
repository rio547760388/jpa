package com.tian;

import com.tian.entity.User;
import com.tian.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class JpaApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
    UserService userService;

	@Test
	public void user() {
	    Optional<User> user = userService.getUserByUsername("tian");

	    System.out.println(user.get());
    }
}
