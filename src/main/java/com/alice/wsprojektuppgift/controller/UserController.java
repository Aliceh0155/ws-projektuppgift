package com.alice.wsprojektuppgift.controller;

import com.alice.wsprojektuppgift.authorities.UserRole;
import com.alice.wsprojektuppgift.model.CustomUser;
import com.alice.wsprojektuppgift.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @GetMapping("/test")
    public String test() {

        return passwordEncoder.encode("123");
    }
    @GetMapping("/adminPage")
    public String adminPage() {

        return "THIS IS THE ADMIN PAGE";
    }

    @GetMapping("/createDefaultUser")
    public CustomUser createDefaultUser(BCryptPasswordEncoder bCryptPasswordEncoder){

        CustomUser customUser = new CustomUser(
                "Benny",
                passwordEncoder.encode("123"),
                UserRole.ADMIN,
                true,
                true,
                true
                ,true
        );
        return userRepository.save(customUser);
    }


}
