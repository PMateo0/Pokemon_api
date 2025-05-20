package com.pruebaTecnica.pokemon_api.model.dto;

import java.util.List;

public class PokemonListResponse {
  private List<PokemonSummaryDto> results;

  public List<PokemonSummaryDto> getResults() {
    return results;
  }

  public void setResults(List<PokemonSummaryDto> results) {
    this.results = results;
  }
}
