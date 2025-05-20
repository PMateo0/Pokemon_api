package com.pruebaTecnica.pokemon_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

  @Bean
  public WebClient pokemonWebClient() {
    int sizeInBytes = 5 * 1024 * 1024;

    ExchangeStrategies strategies = ExchangeStrategies.builder()
        .codecs(configurer -> configurer
            .defaultCodecs()
            .maxInMemorySize(sizeInBytes))
        .build();

    return WebClient.builder()
        .baseUrl("https://pokeapi.co/api/v2")
        .exchangeStrategies(strategies)
        .clientConnector(new ReactorClientHttpConnector(HttpClient.create()))
        .build();
  }
}