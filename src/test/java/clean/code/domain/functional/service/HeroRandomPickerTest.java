package clean.code.domain.functional.service;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Hero;
import clean.code.domain.functional.model.PackType;
import clean.code.domain.ports.server.HeroPersistenceSpi;
import io.vavr.control.Either;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HeroRandomPickerTest {

    @Mock private HeroPersistenceSpi spi;
    @Mock private Random random;
    @Mock private RarityRandomPicker rarityRandomPicker;

    @InjectMocks private HeroRandomPicker heroRandomPicker;

    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3})
    void should_get_random_hero(Integer randomIndex) {
        val rarity = "COMMON";
        val heroesInBdd = List.of(
                Hero.builder().name("hero1").rarity(rarity).build(),
                Hero.builder().name("hero2").rarity(rarity).build(),
                Hero.builder().name("hero3").rarity(rarity).build(),
                Hero.builder().name("hero4").rarity(rarity).build()
        );

        when(spi.findByRarity(rarity)).thenReturn(Either.right(heroesInBdd));
        when(random.nextInt(anyInt())).thenReturn(randomIndex);
        when(rarityRandomPicker.pick(PackType.SILVER)).thenReturn(rarity);
        val expected = heroesInBdd.get(randomIndex);

        val actual = heroRandomPicker.pick(PackType.SILVER);

        assertThat(actual).containsRightSame(expected);

        verify(spi).findByRarity(rarity);
        verify(random).nextInt(heroesInBdd.size());
        verify(rarityRandomPicker).pick(PackType.SILVER);

        verifyNoMoreInteractions(spi, random, rarityRandomPicker);
    }

    @Test
    void should_return_error_when_heroes_not_found() {
        val rarity = "COMMON";
        val expectedError = new ApplicationError("Error while getting heroes", null, null);

        when(spi.findByRarity(rarity)).thenReturn(Either.left(expectedError));
        when(rarityRandomPicker.pick(PackType.SILVER)).thenReturn(rarity);

        val actual = heroRandomPicker.pick(PackType.SILVER);

        assertThat(actual).containsLeftSame(expectedError);

        verify(spi).findByRarity(rarity);
        verify(rarityRandomPicker).pick(PackType.SILVER);

        verifyNoMoreInteractions(spi, rarityRandomPicker);
        verifyNoInteractions(random);
    }

    @Test
    void should_return_error_when_random_index_fail() {
        val rarity = "COMMON";

        when(spi.findByRarity(rarity)).thenReturn(Either.right(List.of(Hero.builder().build())));
        when(random.nextInt(anyInt())).thenThrow(new RuntimeException());
        when(rarityRandomPicker.pick(PackType.SILVER)).thenReturn(rarity);

        val actual = heroRandomPicker.pick(PackType.SILVER);

        assertThat(actual).containsLeftInstanceOf(ApplicationError.class);

        verify(spi).findByRarity(rarity);
        verify(rarityRandomPicker).pick(PackType.SILVER);

        verifyNoMoreInteractions(spi, random, rarityRandomPicker);
    }

    @Test
    void should_return_error_when_get_empty_heroes_by_rarity() {
        val rarity = "COMMON";
        List<Hero> empty = Collections.emptyList();

        when(spi.findByRarity(rarity)).thenReturn(Either.right(empty));
        when(rarityRandomPicker.pick(PackType.SILVER)).thenReturn(rarity);

        val actual = heroRandomPicker.pick(PackType.SILVER);

        assertThat(actual).containsLeftInstanceOf(ApplicationError.class);

        verify(spi).findByRarity(rarity);
        verify(rarityRandomPicker).pick(PackType.SILVER);

        verifyNoMoreInteractions(spi, rarityRandomPicker);
        verifyNoInteractions(random);
    }


}