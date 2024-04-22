package com.nisum.apirest;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nisum.apirest.model.Phone;
import com.nisum.apirest.model.User;
import com.nisum.apirest.repository.UserRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@Log4j2
public class ApiRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiRestApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			User user = User
				.builder()
				.name("Juan Rodriguez")
				.email("juan@rodriguez.org")
				.password(passwordEncoder.encode("hunter2"))
				.phones(List.of(Phone.builder().number("1234567").cityCode("1").countryCode("57").build()))
				.created(LocalDateTime.now())
				.lastLogin(LocalDateTime.now())
				.isActive(true)
				.build();
			User userCreado = userRepository.save(user);
			log.info(userCreado.toString());
		};
	}

}
