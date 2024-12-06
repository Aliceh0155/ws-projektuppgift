package com.alice.wsprojektuppgift;

import com.alice.wsprojektuppgift.entity.FavouriteCharacterEntity;
import com.alice.wsprojektuppgift.repository.IFavouriteCharacterRepository;
import com.alice.wsprojektuppgift.service.CharacterDBService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CharacterDBServiceTest {

  @Mock
  IFavouriteCharacterRepository characterRepository;

  @InjectMocks
  CharacterDBService characterDBService;

  private String characterId;
  private String newImage;

  @BeforeEach
  void setUp() {
    characterId = "123";
    newImage = "https://example.com/new-image.jpg";
  }

  @Test
  @DisplayName("Update character image")
  void testUpdateImage() {

    FavouriteCharacterEntity mockCharacter = new FavouriteCharacterEntity();
    mockCharacter.setApiId(characterId);
    mockCharacter.setImage("https://example.com/old-image.jpg");

    when(characterRepository.findById(characterId)).thenReturn(Optional.of(mockCharacter));
    when(characterRepository.save(mockCharacter)).thenReturn(mockCharacter);

    FavouriteCharacterEntity updatedCharacter = characterDBService.updateImage(characterId, newImage);

    assertNotNull(updatedCharacter);

    assertEquals(newImage, updatedCharacter.getImage());

  }


  @Test
  @DisplayName("When character id is not found")
  void testUpdateImageCharacterNotFound() {

    when(characterRepository.findById(characterId)).thenReturn(Optional.empty());

    NoSuchElementException exception = assertThrows(NoSuchElementException.class, () ->
            characterDBService.updateImage(characterId, newImage));

    assertNotNull(exception);

    assertEquals("Character with id 123 not found", exception.getMessage());

  }

}
