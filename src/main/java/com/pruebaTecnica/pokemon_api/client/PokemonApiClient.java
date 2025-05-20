package com.pruebaTecnica.pokemon_api.client;

import com.pruebaTecnica.pokemon_api.model.dto.PokemonDetailDto;
import com.pruebaTecnica.pokemon_api.model.dto.PokemonListResponse;
import com.pruebaTecnica.pokemon_api.model.dto.PokemonSummaryDto;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
public class PokemonApiClient {

  private static final int PAGE_LIMIT = 100;

  private final WebClient webClient;

  public PokemonApiClient(WebClient pokemonWebClient) {
    this.webClient = pokemonWebClient;
  }

  @Cacheable("allPokemons")
  public List<PokemonSummaryDto> fetchAllSummaries() {
    int pageSize = 20;
    int totalPages = 5;

    return IntStream.range(0, totalPages)
        .mapToObj(i -> {
          int offset = i * pageSize;
          return webClient.get()
              .uri("/pokemon?limit=" + pageSize + "&offset=" + offset)
              .retrieve()
              .bodyToMono(PokemonListResponse.class)
              .block();
        })
        .filter(Objects::nonNull)
        .flatMap(response -> response.getResults().stream())
        .peek(summary -> {
          String[] parts = summary.getUrl().split("/");
          summary.setId(Integer.parseInt(parts[parts.length - 1]));
        })
        .collect(Collectors.toList());
  }

  @Cacheable(value = "pokemonById", key = "#id")
  public PokemonDetailDto fetchDetail(int id) {
    return webClient
        .get()
        .uri("/pokemon/{id}", id)
        .retrieve()
        .bodyToMono(PokemonDetailDto.class)
        .block();
  }

  private static int extractIdFromUrl(String url) {
    String[] parts = url.split("/");
    String last = parts[parts.length - 1];
    String idStr = last.isEmpty() ? parts[parts.length - 2] : last;
    return Integer.parseInt(idStr);
  }

  private record PokeApiListResponse(int count, String next, String previous, List<PokeApiListResult> results) {}
  private record PokeApiListResult(String name, String url) {}
}
