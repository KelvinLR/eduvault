package com.eduvault.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// @Configuration diz ao Spring que esta classe contém configurações globais (@Bean)
@Configuration
public class SecurityConfig {

    // Registramos o BCrypt como o nosso "Embaralhador de Senhas" oficial
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
