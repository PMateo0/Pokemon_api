package com.pruebaTecnica.pokemon_api.controller;

import com.pruebaTecnica.pokemon_api.model.dto.PokemonResponseDto;
import com.pruebaTecnica.pokemon_api.service.PokemonService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PokemonController {

  private final PokemonService service;
  private static final int DEFAULT_TOP = 5;

  public PokemonController(PokemonService service) {
    this.service = service;
  }
  @EventListener(ApplicationReadyEvent.class)
  public void logStartup() {
    System.out.println(">>> La app está lista y escuchando en /pokemons");
  }

  @GetMapping("/pokemons/heaviest")
  public List<PokemonResponseDto> heaviest(
      @RequestParam(name = "top", defaultValue = "5") int top) {
    return service.getTopHeaviest(top);
  }

  @GetMapping("/pokemons/tallest")
  public List<PokemonResponseDto> tallest(
      @RequestParam(name = "top", defaultValue = "" + DEFAULT_TOP) int top) {
    return service.getTopTallest(top);
  }

  @GetMapping("/pokemons/experienced")
  public List<PokemonResponseDto> experienced(
      @RequestParam(name = "top", defaultValue = "" + DEFAULT_TOP) int top) {
    return service.getTopByExperience(top);
  }
}
