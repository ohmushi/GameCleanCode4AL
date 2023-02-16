package clean.code.domain.functional.service;

import clean.code.domain.functional.model.*;
import clean.code.domain.ports.server.PlayerPersistenceSpi;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PackOpenerApiServiceTest {

    @Mock
    private PlayerPersistenceSpi playerSpi;

    @Mock
    private HeroRandomPicker heroRandomPicker;

    @InjectMocks
    private PackOpenerService service;

    @Captor private ArgumentCaptor<Player> playerCaptor;

    @ParameterizedTest
    @CsvSource(value = {"SILVER,1,3", "DIAMOND,2,5"})
    void should_open_pack(String type, Integer nbTokens, Integer nbCards) {
        val givenPlayer = Player.builder()
                .tokens(2)
                .deck(new Deck(List.of(Hero.builder().name("heroAlreadyInHeroDeck").build())))
                .build();

        val h = Hero.builder().rarity("COMMON").build();
        val heroes = new ArrayList<Hero>();
        for(int i = 0; i < nbCards; i++) heroes.add(h);
        val expectedPack = Pack.builder().heroes(heroes).build();
        val expectedHeroesInPlayer = new ArrayList<>(givenPlayer.getDeck().getHeroes());
        expectedHeroesInPlayer.addAll(expectedPack.getHeroes());

        when(playerSpi.findById(givenPlayer.getId())).thenReturn(Option.of(givenPlayer));
        when(heroRandomPicker.pick(anyString())).thenReturn(Either.right(h));

        val actual = service.open(givenPlayer.getId(), PackType.valueOf(type));

        assertThat(actual).containsOnRight(expectedPack);

        verify(playerSpi).findById(givenPlayer.getId());
        verify(playerSpi).save(playerCaptor.capture());

        val saved = playerCaptor.getValue();
        Assertions.assertThat(saved.getId()).isEqualTo(givenPlayer.getId());
        Assertions.assertThat(saved.getTokens()).isEqualTo(givenPlayer.getTokens() - nbTokens);
        Assertions.assertThat(saved.getDeck().getHeroes()).containsExactlyInAnyOrderElementsOf(expectedHeroesInPlayer);

        verifyNoMoreInteractions(playerSpi);
    }

    //TODO player does not have enough tokens for silver and diamond

    // TODO if playerSpi does not find player

}