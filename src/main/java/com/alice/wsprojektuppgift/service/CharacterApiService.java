package com.alice.wsprojektuppgift.service;

import com.alice.wsprojektuppgift.model.CharacterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class CharacterApiService {

        private final RestTemplate restTemplate;

        @Autowired
        public CharacterApiService(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }

        // Hämta alla karaktärer från det externa API:et
        public List<CharacterModel> getAllCharacters() {
            String url = "https://hp-api.herokuapp.com/api/characters";
            CharacterModel[] characters = restTemplate.getForObject(url, CharacterModel[].class);
            return Arrays.asList(characters); // Konvertera till en lista
        }

        // Hämta karaktärer efter hus
        public List<CharacterModel> getCharactersByHouse(String house) {
            String url = "https://hp-api.herokuapp.com/api/characters/house/{house}";
            CharacterModel[] characters = restTemplate.getForObject(url, CharacterModel[].class, house);
            return Arrays.asList(characters); // Konvertera till en lista
        }

}