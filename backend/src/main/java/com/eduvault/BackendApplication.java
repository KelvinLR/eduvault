package com.eduvault;

import com.eduvault.user.Role;
import com.eduvault.user.UserDocument;
import com.eduvault.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	// @Bean do tipo CommandLineRunner executa esse código toda vez que o servidor inicia.
	// É perfeito para o nosso "Data Seed" (Popular o banco de dados com dados iniciais)
	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository) {
		return args -> {
			// Só vamos inserir se o banco estiver vazio
			if (userRepository.count() == 0) {
				System.out.println("O banco está vazio. Criando usuários de teste...");
				
				// Atenção: a senha ainda está em texto puro ("123456").
				// Vamos criptografar isso na Fase 5 quando instalarmos o Spring Security.
				UserDocument student = new UserDocument("student01", "123456", Role.STUDENT);
				UserDocument admin = new UserDocument("admin01", "123456", Role.ADMIN);
				
				userRepository.save(student);
				userRepository.save(admin);
				
				System.out.println("Usuários de teste criados com sucesso!");
			}
		};
	}
}
