package com.alice.wsprojektuppgift.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CustomUserDTO {

        @NotBlank
        @Size(min = 2, max = 32)
        private String username;

        @NotBlank
        @Size(min = 3, max = 64)
        private String password;


        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
}