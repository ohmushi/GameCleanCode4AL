package clean.code.domain.functional.service;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.*;
import clean.code.domain.ports.server.CardPersistenceSpi;
import clean.code.domain.ports.server.PlayerPersistenceSpi;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PackOpenerApiServiceTest {

    @Mock
    private PlayerPersistenceSpi playerSpi;

    @Mock
    private CardPersistenceSpi cardSpi;

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
                .deck(new Deck(List.of(Card.builder().name("heroAlreadyInHeroDeck").build())))
                .build();

        val h = Hero.builder().rarity("COMMON").build();
        val heroes = new ArrayList<Hero>();
        for(int i = 0; i < nbCards; i++) heroes.add(h);
        val expectedPack = new Pack(heroes);
        val expectedCardsInPlayer = new ArrayList<>(givenPlayer.getDeck().getCards());
        expectedCardsInPlayer.addAll(expectedPack.getHeroes().stream().map(Card::fromHero).toList());
        val expectedPlayer = Player.builder()
                .id(givenPlayer.getId())
                .tokens(givenPlayer.getTokens() - nbTokens)
                .deck(new Deck(expectedCardsInPlayer))
                .build();

        when(playerSpi.findById(givenPlayer.getId())).thenReturn(Option.of(givenPlayer));
        when(heroRandomPicker.pick(any(PackType.class))).thenReturn(Either.right(h));
        when(playerSpi.save(expectedPlayer)).thenReturn(Either.right(expectedPlayer));
        when(cardSpi.save(any(Card.class))).thenAnswer(invocation -> Either.right(invocation.getArgument(0)));

        val actual = service.open(givenPlayer.getId(), PackType.valueOf(type));

        assertThat(actual).containsOnRight(expectedPack);

        verify(playerSpi).findById(givenPlayer.getId());
        verify(playerSpi).save(playerCaptor.capture());

        val saved = playerCaptor.getValue();
        Assertions.assertThat(saved.getId()).isEqualTo(givenPlayer.getId());
        Assertions.assertThat(saved.getTokens()).isEqualTo(givenPlayer.getTokens() - nbTokens);
        Assertions.assertThat(saved.getDeck().getCards())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactlyInAnyOrderElementsOf(expectedCardsInPlayer);


        verify(heroRandomPicker, times(nbCards)).pick(any(PackType.class));
        verifyNoMoreInteractions(playerSpi, heroRandomPicker);
    }

    @ParameterizedTest
    @CsvSource(value = {"0,1", "1,2", "0,3"})
    void should_not_open_pack_if_player_does_not_have_enough_tokens(Integer playerTokens, Integer requiredNbTokens) {
        try(MockedStatic<OpenPackConfigurationFactory> factory = mockStatic(OpenPackConfigurationFactory.class)) {

            factory.when(() -> OpenPackConfigurationFactory.forType(any()))
                    .thenReturn(new OpenPackConfiguration(0, requiredNbTokens, 0, 0, 0));
            val givenPlayer = Player.builder()
                    .tokens(playerTokens) // not enough tokens
                    .build();
            val expectedError = new ApplicationError(
                    "Player does not have enough tokens for this pack", givenPlayer, null);

            when(playerSpi.findById(givenPlayer.getId())).thenReturn(Option.of(givenPlayer));

            val actual = service.open(givenPlayer.getId(), PackType.SILVER);

            assertThat(actual).containsOnLeft(expectedError);

            verify(playerSpi).findById(givenPlayer.getId());
            verifyNoInteractions(heroRandomPicker);
            verifyNoMoreInteractions(playerSpi);
        }

    }

    @Test
    void should_return_error_when_player_not_found() {
        val givenId = UUID.randomUUID();
        val expectedError = new ApplicationError("Player not found", givenId, null);

        when(playerSpi.findById(givenId)).thenReturn(Option.none());

        val actual = service.open(givenId, PackType.SILVER);

        assertThat(actual).containsOnLeft(expectedError);
        verify(playerSpi).findById(givenId);
        verifyNoInteractions(heroRandomPicker);
        verifyNoMoreInteractions(playerSpi);
    }

    @Test
    void should_return_error_when_hero_random_picker_fail() {
        val givenPlayer = Player.builder()
                .nickname("player")
                .tokens(1)
                .deck(Deck.empty())
                .build();
        val expectedError = new ApplicationError("Error while picking random hero", null, null);

        when(playerSpi.findById(givenPlayer.getId())).thenReturn(Option.of(givenPlayer));
        when(heroRandomPicker.pick(PackType.SILVER)).thenReturn(Either.left(expectedError));

        val actual = service.open(givenPlayer.getId(), PackType.SILVER);

        assertThat(actual).containsLeftSame(expectedError);

        verify(playerSpi).findById(givenPlayer.getId());
        verify(heroRandomPicker).pick(PackType.SILVER);
        verifyNoMoreInteractions(playerSpi, heroRandomPicker);
    }

}