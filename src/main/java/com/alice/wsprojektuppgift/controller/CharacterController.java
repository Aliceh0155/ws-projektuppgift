package com.alice.wsprojektuppgift.controller;


import com.alice.wsprojektuppgift.model.CharacterModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class CharacterController {

    private final WebClient hpwebClient;


    public CharacterController(WebClient.Builder webClient) {
        this.hpwebClient = webClient
                .baseUrl("https://hp-api.herokuapp.com/api")
                .build();
    }

    @GetMapping("/allCharacters")
    public Mono<List<CharacterModel>> getAllCharacters() {

        return hpwebClient.get()
                .uri("/characters")
                .retrieve()
                .bodyToFlux(CharacterModel.class)
                .collectList();
    }


    @GetMapping("/characterByHouse/{house}")
    public Mono<List<CharacterModel>> getCharacterByHouse(@PathVariable String house) {

        return hpwebClient.get()
                .uri("/characters/house/{house}", house)
                .retrieve()
                .bodyToFlux(CharacterModel.class)
                .collectList();
    }

}
