package com.alice.wsprojektuppgift.controller;

import com.alice.wsprojektuppgift.authorities.UserRole;
import com.alice.wsprojektuppgift.config.security.CustomUserDetails;
import com.alice.wsprojektuppgift.config.security.CustomUserDetailsService;
import com.alice.wsprojektuppgift.config.security.JwtUtil;
import com.alice.wsprojektuppgift.entity.CustomUser;
import com.alice.wsprojektuppgift.model.dto.CustomUserLoginDTO;
import jakarta.validation.Valid;
import com.alice.wsprojektuppgift.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

  private final IUserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final AuthenticationManager authenticationManager;
  private final CustomUserDetailsService customUserDetailsService;


  @Autowired
  public UserController(IUserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
    this.authenticationManager = authenticationManager;
    this.customUserDetailsService = customUserDetailsService;
  }


  @PostMapping("/login")
  public ResponseEntity<String> loginUser(@Valid @RequestBody CustomUserLoginDTO userDTO) {
    UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword());
    Authentication authentication = authenticationManager.authenticate(authenticationToken);

    CustomUserDetails customUser = (CustomUserDetails) authentication.getPrincipal();

    if (customUser instanceof CustomUserDetails customUserDetails) {
      String token = jwtUtil.generateJwtToken(customUserDetails.getUsername(), "ADMIN");
      return ResponseEntity.ok(token);
    } else {
      throw new IllegalStateException("Authenticated principal is not of type CustomUserDetails");
    }
  }


  @GetMapping("/register")
  public String showRegisterPage() {
    return "Register page accessible";
  }


  @PostMapping("/register")
  public ResponseEntity<String> registerUser(@RequestBody @Valid CustomUserLoginDTO userDto) {
    try {
      if (userRepository.existsByUsername(userDto.getUsername())) {
        return ResponseEntity.badRequest().body("Username is already taken");
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

      return ResponseEntity.ok("User registered successfully");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
    }
  }


  @GetMapping("/createDefaultUser")
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

  @GetMapping("/adminPage")
  public String adminPage() {

    return "THIS IS THE ADMIN PAGE";
  }


}
