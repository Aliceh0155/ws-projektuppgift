package com.alice.wsprojektuppgift.controller;

import com.alice.wsprojektuppgift.config.security.CustomUserDetails;
import com.alice.wsprojektuppgift.config.security.JwtUtil;
import com.alice.wsprojektuppgift.entity.CustomUser;
import com.alice.wsprojektuppgift.model.dto.CustomUserLoginDTO;
import com.alice.wsprojektuppgift.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

  private final UserService userService;
  private final JwtUtil jwtUtil;
  private final AuthenticationManager authenticationManager;


  @Autowired
  public UserController(UserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
    this.userService = userService;
    this.jwtUtil = jwtUtil;
    this.authenticationManager = authenticationManager;
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
      return ResponseEntity.badRequest().body("Invalid credentials");
    }
  }


  @PostMapping("/register")
  public ResponseEntity<String> registerUser(@RequestBody @Valid CustomUserLoginDTO userDto) {
    try {
      String result = userService.registerUser(userDto);

      if (result.equals("Username is already taken")) {
        return ResponseEntity.badRequest().body(result);
      }

      return ResponseEntity.status(201).body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest()
              .body("Could not register user: " + e.getMessage());
    }
  }


  @GetMapping("/createDefaultUser")
  public CustomUser createDefaultUser() {
    return userService.createDefaultUser();
  }

  @GetMapping("/adminPage")
  public String adminPage() {
    return userService.getAdminPageContent();
  }


}
