package com.eduvault;

import com.eduvault.user.Role;
import com.eduvault.user.UserDocument;
import com.eduvault.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			userRepository.deleteAll();
			
			System.out.println("Criando usuários de teste com senhas seguras (BCrypt)...");
			
			String senhaCriptografada = passwordEncoder.encode("123456");
			
			UserDocument student = new UserDocument("student01", senhaCriptografada, Role.STUDENT);
			UserDocument admin = new UserDocument("admin01", senhaCriptografada, Role.ADMIN);
			
			userRepository.save(student);
			userRepository.save(admin);
			
			System.out.println("Usuários criados! As senhas não estão mais em texto puro.");
		};
	}
}
