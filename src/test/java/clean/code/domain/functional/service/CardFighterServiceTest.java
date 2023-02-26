package clean.code.domain.functional.service;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Card;
import clean.code.domain.functional.model.FightResult;
import clean.code.domain.ports.server.CardPersistenceSpi;
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
    CardPersistenceSpi spi;

    @Test
    void should_fight() {
        val attackerId = UUID.randomUUID();
        val defenderId = UUID.randomUUID();

        val attacker = Card.builder().id(attackerId).hp(10).armor(10).power(10).speciality("TANK").rarity("COMMON").build();
        val defender = Card.builder().id(defenderId).hp(5).armor(5).power(5).speciality("TANK").rarity("COMMON").build();

        when(spi.findById(attackerId)).thenReturn(Option.of(attacker));
        when(spi.findById(defenderId)).thenReturn(Option.of(defender));

        when(spi.save(any(Card.class))).thenReturn(Either.right(attacker));

        val actual = cardFighterService.fight(attackerId, defenderId);

        VavrAssertions.assertThat(actual).containsRightInstanceOf(FightResult.class);

        verify(spi).findById(attackerId);
        verify(spi).findById(defenderId);
        verify(spi).save(any(Card.class));

        verifyNoMoreInteractions(spi);
    }

    @Test
    void should_not_fight_if_attacker_not_found() {
        val attackerId = UUID.randomUUID();
        val defenderId = UUID.randomUUID();

        when(spi.findById(attackerId)).thenReturn(Option.none());

        val actual = cardFighterService.fight(attackerId, defenderId);

        VavrAssertions.assertThat(actual).containsLeftInstanceOf(ApplicationError.class);

        verify(spi).findById(attackerId);

        verifyNoMoreInteractions(spi);
    }

    @Test
    void should_not_fight_if_defender_not_found() {
        val attackerId = UUID.randomUUID();
        val defenderId = UUID.randomUUID();

        val attacker = Card.builder().id(attackerId).hp(10).armor(10).power(10).build();

        when(spi.findById(attackerId)).thenReturn(Option.of(attacker));
        when(spi.findById(defenderId)).thenReturn(Option.none());

        val actual = cardFighterService.fight(attackerId, defenderId);

        VavrAssertions.assertThat(actual).containsLeftInstanceOf(ApplicationError.class);

        verify(spi).findById(attackerId);
        verify(spi).findById(defenderId);

        verifyNoMoreInteractions(spi);
    }

    @Test
    void should_not_fight_if_defender_level_below() {
        val attackerId = UUID.randomUUID();
        val defenderId = UUID.randomUUID();

        val attacker = Card.builder().id(attackerId).level(10).build();
        val defender = Card.builder().id(defenderId).level(5).build();

        when(spi.findById(attackerId)).thenReturn(Option.of(attacker));
        when(spi.findById(defenderId)).thenReturn(Option.of(defender));

        val actual = cardFighterService.fight(attackerId, defenderId);

        VavrAssertions.assertThat(actual).containsLeftInstanceOf(ApplicationError.class);

        verify(spi).findById(attackerId);
        verify(spi).findById(defenderId);

        verifyNoMoreInteractions(spi);
    }
}