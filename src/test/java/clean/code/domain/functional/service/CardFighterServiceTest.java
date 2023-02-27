package clean.code.domain.functional.service;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Card;
import clean.code.domain.functional.model.FightResult;
import clean.code.domain.functional.model.Player;
import clean.code.domain.ports.server.CardPersistenceSpi;
import clean.code.domain.ports.server.PlayerPersistenceSpi;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.val;
import org.assertj.vavr.api.VavrAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardFighterServiceTest {
    @InjectMocks CardFighterService cardFighterService;

    @Mock
    CardPersistenceSpi cardSpi;

    @Mock
    PlayerPersistenceSpi playerSpi;

    @Test
    void should_fight() {
        val attackerId = UUID.randomUUID();
        val defenderId = UUID.randomUUID();

        val givenPlayer = Player.builder().id(UUID.randomUUID()).build();
        val attacker = Card.builder().id(attackerId).hp(10).armor(10).power(10).speciality("TANK").rarity("COMMON")
                .playerId(givenPlayer.getId().toString()).build();
        val defender = Card.builder().id(defenderId).hp(5).armor(5).power(5).speciality("TANK").rarity("COMMON").build();



        when(cardSpi.findById(attackerId)).thenReturn(Option.of(attacker));
        when(cardSpi.findById(defenderId)).thenReturn(Option.of(defender));

        when(playerSpi.findById(UUID.fromString(attacker.getPlayerId()))).thenReturn(Option.of(givenPlayer));

        when(cardSpi.save(any(Card.class))).thenReturn(Either.right(attacker));
        when(playerSpi.save(any(Player.class))).thenReturn(Either.right(givenPlayer));

        val actual = cardFighterService.fight(attackerId, defenderId);

        VavrAssertions.assertThat(actual).containsRightInstanceOf(FightResult.class);

        verify(cardSpi).findById(attackerId);
        verify(cardSpi).findById(defenderId);
        verify(cardSpi).save(any(Card.class));

        verifyNoMoreInteractions(cardSpi);
    }

    @Test
    void should_not_fight_if_attacker_not_found() {
        val attackerId = UUID.randomUUID();
        val defenderId = UUID.randomUUID();

        when(cardSpi.findById(attackerId)).thenReturn(Option.none());

        val actual = cardFighterService.fight(attackerId, defenderId);

        VavrAssertions.assertThat(actual).containsLeftInstanceOf(ApplicationError.class);

        verify(cardSpi).findById(attackerId);

        verifyNoMoreInteractions(cardSpi);
    }

    @Test
    void should_not_fight_if_defender_not_found() {
        val attackerId = UUID.randomUUID();
        val defenderId = UUID.randomUUID();

        val attacker = Card.builder().id(attackerId).hp(10).armor(10).power(10).build();

        when(cardSpi.findById(attackerId)).thenReturn(Option.of(attacker));
        when(cardSpi.findById(defenderId)).thenReturn(Option.none());

        val actual = cardFighterService.fight(attackerId, defenderId);

        VavrAssertions.assertThat(actual).containsLeftInstanceOf(ApplicationError.class);

        verify(cardSpi).findById(attackerId);
        verify(cardSpi).findById(defenderId);

        verifyNoMoreInteractions(cardSpi);
    }

    @Test
    void should_not_fight_if_defender_level_below() {
        val attackerId = UUID.randomUUID();
        val defenderId = UUID.randomUUID();

        val attacker = Card.builder().id(attackerId).level(10).build();
        val defender = Card.builder().id(defenderId).level(5).build();

        when(cardSpi.findById(attackerId)).thenReturn(Option.of(attacker));
        when(cardSpi.findById(defenderId)).thenReturn(Option.of(defender));

        val actual = cardFighterService.fight(attackerId, defenderId);

        VavrAssertions.assertThat(actual).containsLeftInstanceOf(ApplicationError.class);

        verify(cardSpi).findById(attackerId);
        verify(cardSpi).findById(defenderId);

        verifyNoMoreInteractions(cardSpi);
    }
}