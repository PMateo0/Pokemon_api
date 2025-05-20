package com.pruebaTecnica.pokemon_api.service;

import com.pruebaTecnica.pokemon_api.model.dto.PokemonDetailDto;
import com.pruebaTecnica.pokemon_api.model.dto.PokemonResponseDto;
import java.util.List;

public interface PokemonService {
  List<PokemonResponseDto> getTopHeaviest(int topN);
  List<PokemonResponseDto> getTopTallest(int topN);
  List<PokemonResponseDto> getTopByExperience(int topN);
}
