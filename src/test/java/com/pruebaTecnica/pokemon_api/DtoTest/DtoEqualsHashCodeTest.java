package com.pruebaTecnica.pokemon_api.DtoTest;

import com.pruebaTecnica.pokemon_api.model.dto.PokemonSummaryDto;
import com.pruebaTecnica.pokemon_api.model.dto.TypeInfoDto;
import com.pruebaTecnica.pokemon_api.model.dto.TypeSlotDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DtoEqualsHashCodeTest {

  @Test
  void typeInfoDtoEqualsAndHashCode() {
    TypeInfoDto info1 = new TypeInfoDto();
    info1.setName("electric");
    info1.setUrl("https://pokeapi.co/api/v2/type/13/");

    TypeInfoDto info2 = new TypeInfoDto();
    info2.setName("electric");
    info2.setUrl("https://pokeapi.co/api/v2/type/13/");

    assertThat(info1).isEqualTo(info2);
    assertThat(info1.hashCode()).isEqualTo(info2.hashCode());

    String toStr = info1.toString();
    assertThat(toStr).contains("electric");
    assertThat(toStr).contains("13");
  }

  @Test
  void typeSlotDtoEqualsAndHashCode() {
    TypeInfoDto nested1 = new TypeInfoDto();
    nested1.setName("grass");
    nested1.setUrl("https://pokeapi.co/api/v2/type/12/");

    TypeSlotDto slot1 = new TypeSlotDto();
    slot1.setSlot(1);
    slot1.setType(nested1);

    TypeInfoDto nested2 = new TypeInfoDto();
    nested2.setName("grass");
    nested2.setUrl("https://pokeapi.co/api/v2/type/12/");

    TypeSlotDto slot2 = new TypeSlotDto();
    slot2.setSlot(1);
    slot2.setType(nested2);

    assertThat(slot1).isEqualTo(slot2);
    assertThat(slot1.hashCode()).isEqualTo(slot2.hashCode());

    String toStr = slot1.toString();
    assertThat(toStr).contains("slot=1");
    assertThat(toStr).contains("grass");
  }

  @Test
  void pokemonSummaryDtoEqualsAndHashCodeToString() {
    PokemonSummaryDto sum1 = new PokemonSummaryDto();
    sum1.setId(7);
    sum1.setName("squirtle");
    sum1.setUrl("https://pokeapi.co/api/v2/pokemon/7/");

    PokemonSummaryDto sum2 = new PokemonSummaryDto();
    sum2.setId(7);
    sum2.setName("squirtle");
    sum2.setUrl("https://pokeapi.co/api/v2/pokemon/7/");

    assertThat(sum1).isEqualTo(sum2);
    assertThat(sum1.hashCode()).isEqualTo(sum2.hashCode());

    String toStr = sum1.toString();
    assertThat(toStr).contains("id=7");
    assertThat(toStr).contains("squirtle");
    assertThat(toStr).contains("7/");
  }
}
