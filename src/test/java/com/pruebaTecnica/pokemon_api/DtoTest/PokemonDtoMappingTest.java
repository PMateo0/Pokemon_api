package com.pruebaTecnica.pokemon_api.DtoTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pruebaTecnica.pokemon_api.model.dto.PokemonDetailDto;
import com.pruebaTecnica.pokemon_api.model.dto.PokemonListResponse;
import com.pruebaTecnica.pokemon_api.model.dto.PokemonResponseDto;
import com.pruebaTecnica.pokemon_api.model.dto.PokemonSummaryDto;
import com.pruebaTecnica.pokemon_api.model.dto.TypeSlotDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PokemonDtoMappingTest {

  private ObjectMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
  }

  @Test
  void deserializesPokemonDetailDto() throws Exception {
    String json = "{"
        + "\"id\":25,"
        + "\"name\":\"pikachu\","
        + "\"weight\":60,"
        + "\"height\":4,"
        + "\"base_experience\":112,"
        + "\"types\":[{"
        +     "\"slot\":1,"
        +     "\"type\":{"
        +         "\"name\":\"electric\","
        +         "\"url\":\"https://pokeapi.co/api/v2/type/13/\"}"
        + "}]}";

    PokemonDetailDto detail = mapper.readValue(json, PokemonDetailDto.class);

    assertThat(detail.getId()).isEqualTo(25);
    assertThat(detail.getName()).isEqualTo("pikachu");
    assertThat(detail.getWeight()).isEqualTo(60);
    assertThat(detail.getHeight()).isEqualTo(4);
    assertThat(detail.getBaseExperience()).isEqualTo(112);
    assertThat(detail.getTypes()).hasSize(1);

    TypeSlotDto slot = detail.getTypes().get(0);
    assertThat(slot.getSlot()).isEqualTo(1);
    assertThat(slot.getType()).isNotNull();
    assertThat(slot.getType().getName()).isEqualTo("electric");
    assertThat(slot.getType().getUrl()).isEqualTo("https://pokeapi.co/api/v2/type/13/");
  }

  @Test
  void deserializesPokemonListResponse() throws Exception {
    String json = "{\"results\":[{\"name\":\"bulbasaur\",\"url\":\"https://pokeapi.co/api/v2/pokemon/1/\"}]}";

    PokemonListResponse listResponse = mapper.readValue(json, PokemonListResponse.class);
    assertThat(listResponse.getResults()).hasSize(1);

    PokemonSummaryDto summary = listResponse.getResults().get(0);
    assertThat(summary.getName()).isEqualTo("bulbasaur");
    assertThat(summary.getUrl()).isEqualTo("https://pokeapi.co/api/v2/pokemon/1/");
    // id no presente en JSON, queda como default 0
    assertThat(summary.getId()).isZero();
  }

  @Test
  void serializesPokemonResponseDto() throws Exception {
    List<String> types = List.of("water", "flying");
    PokemonResponseDto responseDto = new PokemonResponseDto(7, "squirtle", 90, 5, 63, types);

    String json = mapper.writeValueAsString(responseDto);
    assertThat(json).contains("\"id\":7");
    assertThat(json).contains("\"name\":\"squirtle\"");
    assertThat(json).contains("\"weight\":90");
    assertThat(json).contains("\"height\":5");
    assertThat(json).contains("\"baseExperience\":63");
    assertThat(json).contains("\"types\":[\"water\",\"flying\"]");
  }
}

