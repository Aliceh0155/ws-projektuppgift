package com.alice.wsprojektuppgift.service;

import com.alice.wsprojektuppgift.authorities.UserRole;
import com.alice.wsprojektuppgift.config.security.CustomUserDetails;
import com.alice.wsprojektuppgift.config.security.CustomUserDetailsService;
import com.alice.wsprojektuppgift.config.security.JwtUtil;
import com.alice.wsprojektuppgift.entity.CustomUser;
import com.alice.wsprojektuppgift.model.dto.CustomUserLoginDTO;
import com.alice.wsprojektuppgift.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final IUserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final AuthenticationManager authenticationManager;
  private final CustomUserDetailsService customUserDetailsService;

  @Autowired
  public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
    this.authenticationManager = authenticationManager;
    this.customUserDetailsService = customUserDetailsService;
  }


  public String registerUser(CustomUserLoginDTO userDto) {
    if (userRepository.existsByUsername(userDto.getUsername())) {
      return "Username is already taken";
    }

    CustomUser newUser = new CustomUser();
    newUser.setUsername(userDto.getUsername());
    newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
    newUser.setUserRole(UserRole.USER);
    newUser.setAccountNonExpired(true);
    newUser.setAccountNonLocked(true);
    newUser.setCredentialsNonExpired(true);
    newUser.setEnabled(true);

    userRepository.save(newUser);
    return "User registered successfully";
  }

  //Skapa en default user
  public CustomUser createDefaultUser() {
    CustomUser customUser = new CustomUser(
            "Benny",
            passwordEncoder.encode("123"),
            UserRole.ADMIN,
            true,
            true,
            true,
            true
    );
    return userRepository.save(customUser);
  }

  // Admin sida
  public String getAdminPageContent() {
    return "THIS IS THE ADMIN PAGE";
  }

}
