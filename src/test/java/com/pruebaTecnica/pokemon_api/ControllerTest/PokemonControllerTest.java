package com.pruebaTecnica.pokemon_api.ControllerTest;

import com.pruebaTecnica.pokemon_api.model.dto.PokemonResponseDto;
import com.pruebaTecnica.pokemon_api.service.PokemonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.main.allow-bean-definition-overriding=true")
class PokemonControllerIntegrationTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @MockitoBean
  private PokemonService pokemonService;

  private String url(String path) {
    return "http://localhost:" + port + "/pokemons" + path;
  }

  @Test
  void testGetHeaviest() {
    List<PokemonResponseDto> heaviest = List.of(
        new PokemonResponseDto(143, "Snorlax", 4600, 210, 160, List.of("normal")),
        new PokemonResponseDto(130, "Gyarados", 2350, 650, 189, List.of("water", "flying")),
        new PokemonResponseDto(95, "Onix", 2100, 880, 77, List.of("rock", "ground"))
    );
    when(pokemonService.getTopHeaviest(eq(3))).thenReturn(heaviest);

    var response = restTemplate.getForEntity(url("/heaviest?top=3"), PokemonResponseDto[].class);

    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    PokemonResponseDto[] body = response.getBody();
    assertThat(body).hasSize(3);
    assertThat(List.of(body)).extracting(PokemonResponseDto::getName)
        .containsExactly("Snorlax", "Gyarados", "Onix");
  }

  @Test
  void testGetTallest() {
    List<PokemonResponseDto> tallest = List.of(
        new PokemonResponseDto(95, "Onix", 2100, 880, 77, List.of("rock", "ground")),
        new PokemonResponseDto(130, "Gyarados", 2350, 650, 189, List.of("water", "flying"))
    );
    when(pokemonService.getTopTallest(eq(2))).thenReturn(tallest);

    var response = restTemplate.getForEntity(url("/tallest?top=2"), PokemonResponseDto[].class);

    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    PokemonResponseDto[] body = response.getBody();
    assertThat(body).hasSize(2);
    assertThat(List.of(body)).extracting(PokemonResponseDto::getName)
        .containsExactly("Onix", "Gyarados");
  }

  @Test
  void testGetExperienced() {
    List<PokemonResponseDto> experienced = List.of(
        new PokemonResponseDto(6, "Charizard", 905, 170, 240, List.of("fire", "flying")),
        new PokemonResponseDto(130, "Gyarados", 2350, 650, 189, List.of("water", "flying")),
        new PokemonResponseDto(143, "Snorlax", 4600, 210, 160, List.of("normal")),
        new PokemonResponseDto(25, "Pikachu", 60, 40, 112, List.of("electric"))
    );
    when(pokemonService.getTopByExperience(eq(4))).thenReturn(experienced);

    var response = restTemplate.getForEntity(url("/experienced?top=4"), PokemonResponseDto[].class);

    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    PokemonResponseDto[] body = response.getBody();
    assertThat(body).hasSize(4);
    assertThat(List.of(body)).extracting(PokemonResponseDto::getName)
        .containsExactly("Charizard", "Gyarados", "Snorlax", "Pikachu");
  }
}
