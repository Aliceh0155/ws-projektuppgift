package com.alice.wsprojektuppgift.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppPasswordConfig {

    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        System.out.println("TEST");
        System.out.println("JAG ÄR BEANEN");
        return new BCryptPasswordEncoder(15);
    }
}