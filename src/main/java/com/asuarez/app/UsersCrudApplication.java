package com.asuarez.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asuarez.app.entity.User;
import com.asuarez.app.service.UserService;

@SpringBootApplication
public class UsersCrudApplication {
	
	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(UsersCrudApplication.class, args);
	}
	
	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello Mr/Ms %s!", name);
	}
	
	@Bean
	public CommandLineRunner dummy() {
		return (args) -> {
			User user = new User();
			user.setEmail("alejandro@gmail.com");
			user.setEnable(true);
			user.setLastname("Suarez Bejarano");
			user.setName("Alejandro Main");
			userService.save(user);
			
			User user2 = new User();
			user2.setEmail("restrepo@gmail.com");
			user2.setEnable(false);
			user2.setLastname("Restrepo");
			user2.setName("Juan Jose Main");
			userService.save(user2);
		};
	}

}
