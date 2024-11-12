package com.alice.wsprojektuppgift.config.security;

import com.alice.wsprojektuppgift.authorities.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login").permitAll()
                        .requestMatchers("/allCharacters").permitAll()
                        .requestMatchers("/adminPage").hasRole(UserRole.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/createDefaultUser").permitAll()
                        .anyRequest()
                        .authenticated()
                )

                .formLogin(withDefaults());
        return http.build();
    }

//    //Debug user
//    @Bean
//    public UserDetailsService userDetailsService (PasswordEncoder bcryptPasswordEncoder) {
//        UserDetails user =
//                User.builder()
//                        .username("Alice")
//                        .password(bcryptPasswordEncoder.encode("123"))
//                        .authorities(UserRole.USER.getAuthorities())
//                        .build();
//        return new InMemoryUserDetailsManager(user);
//    }
}
