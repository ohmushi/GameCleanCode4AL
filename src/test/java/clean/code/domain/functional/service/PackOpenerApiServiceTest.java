package clean.code.domain.functional.service;

import clean.code.domain.functional.model.*;
import clean.code.domain.ports.server.HeroPersistenceSpi;
import clean.code.domain.ports.server.PlayerPersistenceSpi;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

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
    private PackOpenerApiService service;

    @Captor private ArgumentCaptor<Player> playerCaptor;

    @Test
    void should_open_silver_pack() {
        val givenPlayer = Player.builder()
                .tokens(1)
                .deck(new Deck(List.of(Hero.builder().name("heroAlreadyInHeroDeck").build())))
                .build();

        val h = Hero.builder().rarity("COMMON").build();
        val expectedPack = Pack.builder().heroes(List.of(h, h, h)).build();
        val expectedHeroesInPlayer = new ArrayList<>(givenPlayer.getDeck().getHeroes());
        expectedHeroesInPlayer.addAll(expectedPack.getHeroes());

        val expectedPlayer = givenPlayer
                .withTokens(0)
                .withDeck(Deck.builder().heroes(expectedHeroesInPlayer).build());

        when(playerSpi.findById(givenPlayer.getId())).thenReturn(Option.of(givenPlayer));
        when(heroRandomPicker.pick("COMMON")).thenReturn(Either.right(h));

        val actual = service.open(givenPlayer.getId(), PackType.SILVER);

        assertThat(actual).containsOnRight(expectedPack);

        verify(playerSpi).findById(givenPlayer.getId());
        verify(playerSpi).save(playerCaptor.capture());
        val saved = playerCaptor.getValue();
        Assertions.assertThat(saved.getId()).isEqualTo(givenPlayer.getId());
        Assertions.assertThat(saved.getTokens()).isEqualTo(0);
        Assertions.assertThat(saved.getDeck().getHeroes()).containsExactlyInAnyOrderElementsOf(expectedHeroesInPlayer);

        verifyNoMoreInteractions(playerSpi);
    }

    //TODO player does not have enough tokens for silver and diamond

    // TODO if playerSpi does not find player

}