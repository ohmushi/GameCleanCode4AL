package clean.code.domain.functional.model;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CardTest {

    @Test
    void should_win_fight() {
        val card = Card.builder().hp(100).power(100).armor(100).speciality("TANK").rarity("COMMON").build();
        val opponent = Card.builder().hp(10).power(10).armor(10).speciality("TANK").rarity("COMMON").build();
        val expected = FightResult.builder().opponent(opponent).won(true).build();

        val actual = card.fight(opponent);
        assertEquals(expected, actual);
    }

    @Test
    void should_lose_fight() {
        val card = Card.builder().hp(10).power(10).armor(10).speciality("TANK").rarity("COMMON").build();
        val opponent = Card.builder().hp(100).power(100).armor(100).speciality("TANK").rarity("COMMON").build();
        val expected = FightResult.builder().opponent(opponent).won(false).build();

        val actual = card.fight(opponent);
        assertEquals(expected, actual);
    }

    @Test
    void should_throw_exception() {
        val card = Card.builder().hp(0).build();
        val opponent = Card.builder().hp(0).build();

        assertThrows(IllegalStateException.class, () -> card.fight(opponent));
    }
}