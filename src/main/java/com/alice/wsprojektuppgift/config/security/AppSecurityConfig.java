package com.alice.wsprojektuppgift.config.security;

import com.alice.wsprojektuppgift.authorities.UserRole;
import com.alice.wsprojektuppgift.config.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {


    private final JwtAuthFilter jwtAuthFilter;

    @Autowired
    public AppSecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,"/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/register").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/", "/login").permitAll()
                        .requestMatchers("/allCharacters").permitAll()
                        .requestMatchers("/adminPage").hasRole(UserRole.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/createDefaultUser").permitAll()
                        .anyRequest()
                        .authenticated()
                )

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
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
