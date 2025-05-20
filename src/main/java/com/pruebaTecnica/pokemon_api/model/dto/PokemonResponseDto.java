package com.pruebaTecnica.pokemon_api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class PokemonResponseDto {
  private int id;
  private String name;
  private int weight;
  private int height;
  private int baseExperience;
  private List<String> types;
}
