package com.alice.wsprojektuppgift;

import com.alice.wsprojektuppgift.authorities.UserRole;
import com.alice.wsprojektuppgift.entity.CustomUser;
import com.alice.wsprojektuppgift.model.dto.CustomUserDTO;
import com.alice.wsprojektuppgift.model.dto.CustomUserLoginDTO;
import com.alice.wsprojektuppgift.repository.IUserRepository;
import com.alice.wsprojektuppgift.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock
  IUserRepository userRepository;
  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  UserService userService;

  CustomUserLoginDTO loginUserDTO;
  CustomUserDTO customUserDTO;

  @BeforeEach
  void setup() {
    loginUserDTO = new CustomUserLoginDTO();
    loginUserDTO.setUsername("newuser");
    loginUserDTO.setPassword("password");

    customUserDTO = new CustomUserDTO();
    customUserDTO.setUsername("newuser");
    customUserDTO.setPassword("password");
  }

  @Test
  @DisplayName("Add a favourite character")
  void testAddFavouriteCharacterToUser() {
    CustomUser customUser = new CustomUser(
            customUserDTO.getUsername(),
            customUserDTO.getPassword(),
            UserRole.USER,
            true, true, true, true
    );
    customUser.setFavouriteCharacters(new ArrayList<>());

    String username = customUserDTO.getUsername();
    String characterId = "1234";

    when(userRepository.findByUsername(username)).thenReturn(Optional.of(customUser));

    String result = userService.addFavoriteCharacterToUser(username, characterId);

    assertEquals("Character ID added to favorites", result);

    assertTrue(customUser.getFavouriteCharacters().contains(characterId));
  }

  @Test
  @DisplayName("Add a favourite character when it is already in the database")
  void testAddWhenAlreadyInFavourites() {
    String characterId = "1234";

    List<String> favoriteCharacters = new ArrayList<>();
    favoriteCharacters.add(characterId);

    CustomUser mockUser = new CustomUser();
    mockUser.setUsername(customUserDTO.getUsername());
    mockUser.setFavouriteCharacters(favoriteCharacters);

    when(userRepository.findByUsername(customUserDTO.getUsername())).thenReturn(Optional.of(mockUser));

    String result = userService.addFavoriteCharacterToUser(customUserDTO.getUsername(), characterId);


    assertEquals("Character ID already in favorites", result);
  }


  @Test
  @DisplayName("Register a new user")
  void testRegisterUser() {

    when(userRepository.existsByUsername(loginUserDTO.getUsername())).thenReturn(false);
    when(passwordEncoder.encode(loginUserDTO.getPassword())).thenReturn("encodedPassword");

    String result = userService.registerUser(loginUserDTO);

    assertEquals("User registered successfully", result);

  }

  @Test
  @DisplayName("Register a user when username already exists")
  void testRegisterUserAlreadyExists() {

    when(userRepository.existsByUsername(loginUserDTO.getUsername())).thenReturn(true);

    String result = userService.registerUser(loginUserDTO);

    assertEquals("Username is already taken", result);
  }

  @Test
  @DisplayName("Create a default user")
  void testCreateDefaultUser() {
    String encodedPassword = "encoded123";
    when(passwordEncoder.encode("123")).thenReturn(encodedPassword);

    CustomUser mockUser = new CustomUser(
            "Benny",
            encodedPassword,
            UserRole.ADMIN,
            true,
            true,
            true,
            true
    );
    when(userRepository.save(any(CustomUser.class))).thenReturn(mockUser);

    CustomUser createdUser = userService.createDefaultUser();

    assertNotNull(createdUser);

    assertEquals("Benny", createdUser.getUsername());

    assertEquals(encodedPassword, createdUser.getPassword());
  }

}
