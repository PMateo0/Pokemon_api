package com.pruebaTecnica.pokemon_api.ClientTest;

import com.pruebaTecnica.pokemon_api.client.PokemonApiClient;
import com.pruebaTecnica.pokemon_api.model.dto.PokemonDetailDto;
import com.pruebaTecnica.pokemon_api.model.dto.PokemonSummaryDto;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PokemonApiClientTest {

  private static MockWebServer mockWebServer;
  private PokemonApiClient client;

  @BeforeAll
  static void startServer() throws IOException {
    mockWebServer = new MockWebServer();
    mockWebServer.start();
  }

  @AfterAll
  static void shutdownServer() throws IOException {
    mockWebServer.shutdown();
  }

  @BeforeEach
  void setUp() {
    String baseUrl = mockWebServer.url("/").toString();
    WebClient webClient = WebClient.builder().baseUrl(baseUrl).build();
    client = new PokemonApiClient(webClient);
  }

  @Test
  void fetchAllSummaries_returnsAllPagesAndMapsIds() throws InterruptedException {
    String pageJson = "{\"results\":[{" + "\"name\":\"pokemon1\"," + "\"url\":\"https://pokeapi.co/api/v2/pokemon/1/\"}]}";
    MockResponse mockResponse = new MockResponse()
        .setHeader("Content-Type", "application/json")
        .setBody(pageJson);
    for (int i = 0; i < 5; i++) {
      mockWebServer.enqueue(mockResponse.clone());
    }
    List<PokemonSummaryDto> summaries = client.fetchAllSummaries();
    assertThat(summaries).hasSize(5);
    for (PokemonSummaryDto summary : summaries) {
      assertThat(summary.getId()).isEqualTo(1);
      assertThat(summary.getName()).isEqualTo("pokemon1");
      assertThat(summary.getUrl()).isEqualTo("https://pokeapi.co/api/v2/pokemon/1/");
    }
  }

  @Test
  void fetchDetail_returnsCorrectPokemonDetail() {
    String detailJson = "{"
        + "\"id\":1,"
        + "\"name\":\"bulbasaur\","
        + "\"weight\":69,"
        + "\"height\":7,"
        + "\"base_experience\":64,"
        + "\"types\":[{"
        +     "\"slot\":1,"
        +     "\"type\":{"
        +         "\"name\":\"grass\","
        +         "\"url\":\"https://pokeapi.co/api/v2/type/12/\"}"
        + "}]}";
    mockWebServer.enqueue(new MockResponse()
        .setHeader("Content-Type", "application/json")
        .setBody(detailJson));

    PokemonDetailDto detail = client.fetchDetail(1);

    assertThat(detail).isNotNull();
    assertThat(detail.getId()).isEqualTo(1);
    assertThat(detail.getName()).isEqualTo("bulbasaur");
    assertThat(detail.getWeight()).isEqualTo(69);
    assertThat(detail.getHeight()).isEqualTo(7);
    assertThat(detail.getBaseExperience()).isEqualTo(64);
    assertThat(detail.getTypes()).hasSize(1);
    assertThat(detail.getTypes().get(0).getSlot()).isEqualTo(1);
    assertThat(detail.getTypes().get(0).getType().getName()).isEqualTo("grass");
    assertThat(detail.getTypes().get(0).getType().getUrl()).isEqualTo("https://pokeapi.co/api/v2/type/12/");
  }
}
