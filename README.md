[![CI](https://github.com/PMateo0/Pokemon_api/actions/workflows/CI.yml/badge.svg)](https://github.com/PMateo0/Pokemon_api/actions/workflows/CI.yml)
[![Docker Publish](https://github.com/PMateo0/Pokemon_api/actions/workflows/docker-publish.yml/badge.svg)](https://github.com/PMateo0/Pokemon_api/actions/workflows/docker-publish.yml)
[![codecov](https://codecov.io/gh/PMateo0/Pokemon_api/graph/badge.svg?token=965V01ZSMR)]

# Pokémon API Service

A Spring Boot service (Java 17) that fetches data from the public [PokeAPI](https://pokeapi.co), caches results with Caffeine, and exposes three “top N” endpoints for weight, height and experience.

---

## Table of Contents

1. [Description](#description)
2. [Architecture & Design](#architecture--design)
3. [Installation](#installation)
4. [Running Locally](#running-locally)
5. [Endpoints](#endpoints)
6. [Configuration](#configuration)
7. [Tests](#tests)

---

## Description

This service uses Spring WebClient to call PokeAPI, transforms the JSON into `PokemonResponseDto` objects, and caches frequent requests in Caffeine (TTL configurable) for faster responses.

---

## Architecture & Design

* **Pattern**: Clean Architecture
* **Layers**:

  * `controller` → REST API (`PokemonController.java`)
  * `service` → business logic (`PokemonService`, `PokemonServiceImpl`)
  * `client` → external API integration (`PokemonApiClient`)
  * `dto` → data-transfer objects (`PokemonResponseDto`, etc.)
* **HTTP Client**: Spring WebClient (`WebClientConfig.java`)
* **Cache**: Caffeine (`CacheConfig.java`)
* **Serialization**: Jackson (Spring Boot default)

---

## Installation

```bash
git clone https://github.com/YOUR_GITHUB_USERNAME/pokemon-api.git
cd pokemon-api
mvn clean package -DskipTests
```

---

## Running Locally

```bash
java -jar target/pokemon-api-0.0.1-SNAPSHOT.jar
```

By default, the service will listen on `http://localhost:8080`.

---

## Endpoints

All endpoints accept an optional query parameter `top` (default: 5). They return a JSON array of `PokemonResponseDto` objects:

```json
{
  "id": 6,
  "name": "charizard",
  "weight": 905,
  "height": 17,
  "baseExperience": 240,
  "types": ["fire", "flying"]
}
```

| Method | Path                    | Description                      | Query Parameter            |
| ------ | ----------------------- | -------------------------------- | -------------------------- |
| GET    | `/pokemons/heaviest`    | Top N Pokémon by weight          | `top` (integer, default 5) |
| GET    | `/pokemons/tallest`     | Top N Pokémon by height          | `top` (integer, default 5) |
| GET    | `/pokemons/experienced` | Top N Pokémon by base experience | `top` (integer, default 5) |

### Examples

```bash
# Top 5 heaviest
curl "http://localhost:8080/pokemons/heaviest?top=5"

# Top 3 tallest
curl "http://localhost:8080/pokemons/tallest?top=3"

# Top 10 most experienced
curl "http://localhost:8080/pokemons/experienced?top=10"
```

---

## Configuration

In `src/main/resources/application.yml` (or `application.properties`) you can customize:

```yaml
pokeapi:
  base-url: https://pokeapi.co/api/v2

spring:
  cache:
    caffeine:
      spec: maximumSize=500,expireAfterWrite=10m

server:
  port: 8080
```

---

## Tests

```bash
mvn test
```

* **Coverage**: JaCoCo generates a report under `target/site/jacoco/index.html`.

