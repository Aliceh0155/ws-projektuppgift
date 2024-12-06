package com.alice.wsprojektuppgift.service;

import com.alice.wsprojektuppgift.authorities.UserRole;
import com.alice.wsprojektuppgift.entity.CustomUser;
import com.alice.wsprojektuppgift.model.dto.CustomUserLoginDTO;
import com.alice.wsprojektuppgift.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

  private final IUserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public String addFavoriteCharacterToUser(String username, String characterId) {
    Optional<CustomUser> userOpt = userRepository.findByUsername(username);
    if (userOpt.isEmpty()) {
      return "User not found";
    }

    CustomUser user = userOpt.get();
    List<String> favoriteCharacterIds = user.getFavouriteCharacters();

    if (!favoriteCharacterIds.contains(characterId)) {
      favoriteCharacterIds.add(characterId);
      user.setFavouriteCharacters(favoriteCharacterIds);
      userRepository.save(user);
      return "Character ID added to favorites";
    }

    return "Character ID already in favorites";
  }

  public List<String> getUserFavorites(String username) {
    Optional<CustomUser> userOpt = userRepository.findByUsername(username);
    if (userOpt.isEmpty()) {
      throw new NoSuchElementException("User not found");
    }
    CustomUser user = userOpt.get();
    return user.getFavouriteCharacters();
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
