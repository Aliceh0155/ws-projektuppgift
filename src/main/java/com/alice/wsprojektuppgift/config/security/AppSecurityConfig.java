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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


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
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.GET,"/getAllCharactersFromDatabase","allCharactersFromApi","/characterByHouse/").permitAll()
                    .requestMatchers(HttpMethod.POST, "/","/login","/register").permitAll()
                    .requestMatchers(HttpMethod.GET,"/getFavouriteCharacters","/character/").authenticated()
                    .requestMatchers(HttpMethod.POST, "/addFavoriteCharacterToDatabase/").authenticated()
                    .requestMatchers("/createDefaultUser","/adminPage","/saveCharactersToDatabase","/updateImage/","/deleteCharacter/").hasRole(UserRole.ADMIN.name())
                    .anyRequest()
                    .authenticated()
            )
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

}
