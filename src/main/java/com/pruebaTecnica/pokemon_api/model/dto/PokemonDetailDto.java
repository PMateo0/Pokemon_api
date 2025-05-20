package com.pruebaTecnica.pokemon_api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class PokemonDetailDto {
  private int id;
  private String name;
  private int weight;
  private int height;

  @JsonProperty("base_experience")
  private int baseExperience;

  private List<TypeSlotDto> types;
}