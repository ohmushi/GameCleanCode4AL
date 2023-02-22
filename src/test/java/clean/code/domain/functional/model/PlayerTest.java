package clean.code.domain.functional.model;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class PlayerTest {

    @Test
    void should_add_heroes_in_empty_deck() {
        val player = Player.builder().deck(Deck.empty()).build();
        val cards = List.of(
                Card.builder().name("hero1").build(),
                Card.builder().name("hero2").build()
        );
        val expected = new Deck(cards);

        val actual = player.addCardsInDeck(cards);

        assertThat(actual.getDeck()).isEqualTo(expected);
    }

    @Test
    void should_add_heroes_in_already_filled_deck() {
        val player = Player.builder()
                .deck(new Deck(List.of(Card.builder().name("alreadyInDeck").build())))
                .build();
        val cards = List.of(
                Card.builder().name("hero1").build(),
                Card.builder().name("hero2").build()
        );

        val actual = player.addCardsInDeck(cards);

        assertThat(actual.getDeck().getCards())
                .containsExactlyInAnyOrder(
                        player.getDeck().getCards().get(0),
                        cards.get(0),
                        cards.get(1)
                );
    }
}