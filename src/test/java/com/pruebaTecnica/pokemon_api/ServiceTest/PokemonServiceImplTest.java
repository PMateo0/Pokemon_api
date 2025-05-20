package com.pruebaTecnica.pokemon_api.ServiceTest;

import com.pruebaTecnica.pokemon_api.client.PokemonApiClient;
import com.pruebaTecnica.pokemon_api.model.dto.PokemonDetailDto;
import com.pruebaTecnica.pokemon_api.model.dto.PokemonSummaryDto;
import com.pruebaTecnica.pokemon_api.model.dto.TypeInfoDto;
import com.pruebaTecnica.pokemon_api.model.dto.TypeSlotDto;
import com.pruebaTecnica.pokemon_api.service.PokemonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PokemonServiceImplTest {

  private PokemonApiClient client;
  private PokemonServiceImpl service;

  @BeforeEach
  void setUp() {
    client = mock(PokemonApiClient.class);
    service = new PokemonServiceImpl(client);

    List<PokemonSummaryDto> summaries = List.of(
        summary("snorlax", 143),
        summary("charizard", 6),
        summary("onix", 95),
        summary("pikachu", 25),
        summary("gyarados", 130)
    );

    Map<Integer, PokemonDetailDto> details = Map.of(
        143, detail("Snorlax", 4600, 210, 160, "normal"),
        6, detail("Charizard", 905, 170, 240, "fire", "flying"),
        95, detail("Onix", 2100, 880, 77, "rock", "ground"),
        25, detail("Pikachu", 60, 40, 112, "electric"),
        130, detail("Gyarados", 2350, 650, 189, "water", "flying")
    );

    when(client.fetchAllSummaries()).thenReturn(summaries);
    details.forEach((id, dto) -> when(client.fetchDetail(id)).thenReturn(dto));
  }

  private PokemonSummaryDto summary(String name, int id) {
    PokemonSummaryDto dto = new PokemonSummaryDto();
    dto.setName(name);
    dto.setId(id);
    return dto;
  }

  private PokemonDetailDto detail(String name, int weight, int height, int exp, String... types) {
    PokemonDetailDto dto = new PokemonDetailDto();
    dto.setName(name);
    dto.setWeight(weight);
    dto.setHeight(height);
    dto.setBaseExperience(exp);

    List<TypeSlotDto> slots = new java.util.ArrayList<>();
    for (int i = 0; i < types.length; i++) {
      TypeInfoDto info = new TypeInfoDto();
      info.setName(types[i]);
      TypeSlotDto slot = new TypeSlotDto();
      slot.setSlot(i + 1);
      slot.setType(info);
      slots.add(slot);
    }
    dto.setTypes(slots);
    return dto;
  }

  @Test
  void testGetTopHeaviest() {
    var result = service.getTopHeaviest(3);
    assertEquals(3, result.size());
    assertEquals("Snorlax", result.get(0).getName());
    assertEquals("Gyarados", result.get(1).getName());  // <-- corregido
    assertEquals("Onix", result.get(2).getName());
  }

  @Test
  void testGetTopTallest() {
    var result = service.getTopTallest(2);
    assertEquals(2, result.size());
    assertEquals("Onix", result.get(0).getName());
    assertEquals("Gyarados", result.get(1).getName());
  }

  @Test
  void testGetTopByExperience() {
    var result = service.getTopByExperience(4);
    assertEquals(4, result.size());
    assertEquals("Charizard", result.get(0).getName());
    assertEquals("Gyarados", result.get(1).getName());
    assertEquals("Snorlax", result.get(2).getName());
    assertEquals("Pikachu", result.get(3).getName());
  }

  @Test
  void testTypeMapping() {
    var result = service.getTopHeaviest(1);
    assertEquals(List.of("normal"), result.get(0).getTypes());
  }
}
