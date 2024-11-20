package com.alice.wsprojektuppgift.controller;

import com.alice.wsprojektuppgift.authorities.UserRole;
import com.alice.wsprojektuppgift.config.security.JwtUtil;
import com.alice.wsprojektuppgift.model.CustomUser;
import com.alice.wsprojektuppgift.model.dto.CustomUserDTO;
import com.alice.wsprojektuppgift.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody @Valid CustomUserDTO userDto) {
        try {

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword());

            authenticationManager.authenticate(authenticationToken);

            String token = jwtUtil.generateToken(userDto.getUsername());

            return ResponseEntity.ok("Bearer " + token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }


    @GetMapping("/register")
    public String showRegisterPage() {
        return "Register page accessible";
    }


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid CustomUserDTO userDto) {
//        if (userRepository.existsByUsername(userDto.getUsername())) {
//            return ResponseEntity.badRequest().body("Username is already taken");
//        }
        try {


        CustomUser newUser = new CustomUser();
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setUserRole(UserRole.USER);
        newUser.setAccountNonExpired(true);
        newUser.setAccountNonLocked(true);
        newUser.setCredentialsNonExpired(true);
        newUser.setEnabled(true);

        userRepository.save(newUser);

        return ResponseEntity.ok("User registered successfully");}
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/createDefaultUser")
    public CustomUser createDefaultUser(BCryptPasswordEncoder bCryptPasswordEncoder){

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
    @GetMapping("/test")
    public String test() {

        return passwordEncoder.encode("123");
    }
    @GetMapping("/adminPage")
    public String adminPage() {

        return "THIS IS THE ADMIN PAGE";
    }


}
