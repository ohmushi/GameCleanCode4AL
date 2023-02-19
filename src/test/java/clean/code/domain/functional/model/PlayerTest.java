package clean.code.domain.functional.model;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class PlayerTest {

    @Test
    void should_add_heroes_in_empty_deck() {
        val player = Player.builder().deck(Deck.empty()).build();
        val heroes = List.of(
                Hero.builder().name("hero1").build(),
                Hero.builder().name("hero2").build()
        );
        val expected = new Deck(heroes);

        val actual = player.addHeroesInDeck(heroes);

        assertThat(actual.getDeck()).isEqualTo(expected);
    }

    @Test
    void should_add_heroes_in_already_filled_deck() {
        val player = Player.builder()
                .deck(new Deck(List.of(Hero.builder().name("alreadyInDeck").build())))
                .build();
        val heroes = List.of(
                Hero.builder().name("hero1").build(),
                Hero.builder().name("hero2").build()
        );

        val actual = player.addHeroesInDeck(heroes);

        assertThat(actual.getDeck().getHeroes())
                .containsExactlyInAnyOrder(
                        player.getDeck().getHeroes().get(0),
                        heroes.get(0),
                        heroes.get(1)
                );
    }
}