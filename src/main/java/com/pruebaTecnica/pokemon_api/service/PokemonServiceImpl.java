package com.pruebaTecnica.pokemon_api.service;

import com.pruebaTecnica.pokemon_api.client.PokemonApiClient;
import com.pruebaTecnica.pokemon_api.model.dto.PokemonDetailDto;
import com.pruebaTecnica.pokemon_api.model.dto.PokemonResponseDto;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PokemonServiceImpl implements PokemonService {

  private final PokemonApiClient client;
  private static final int DEFAULT_TOP = 5;

  public PokemonServiceImpl(PokemonApiClient client) {
    this.client = client;
  }

  @Override
  public List<PokemonResponseDto> getTopHeaviest(int topN) {
    return fetchAllDetails().stream()
        .sorted(Comparator.comparingInt(PokemonDetailDto::getWeight).reversed())
        .limit(topN)
        .map(this::toResponseDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<PokemonResponseDto> getTopTallest(int topN) {
    return fetchAllDetails().stream()
        .sorted(Comparator.comparingInt(PokemonDetailDto::getHeight).reversed())
        .limit(topN)
        .map(this::toResponseDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<PokemonResponseDto> getTopByExperience(int topN) {
    return fetchAllDetails().stream()
        .sorted(Comparator.comparingInt(PokemonDetailDto::getBaseExperience).reversed())
        .limit(topN)
        .map(this::toResponseDto)
        .collect(Collectors.toList());
  }

  private List<PokemonDetailDto> fetchAllDetails() {
    return client.fetchAllSummaries().stream()
        .parallel()
        .map(summary -> client.fetchDetail(summary.getId()))
        .collect(Collectors.toList());
  }

  private PokemonResponseDto toResponseDto(PokemonDetailDto d) {
    List<String> typeNames = d.getTypes().stream()
        .map(slot -> slot.getType().getName())
        .toList();
    return new PokemonResponseDto(
        d.getId(),
        d.getName(),
        d.getWeight(),
        d.getHeight(),
        d.getBaseExperience(),
        typeNames
    );
  }
}
