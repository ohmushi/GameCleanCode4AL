package clean.code.domain.functional.service.validation;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Deck;
import clean.code.domain.functional.model.Player;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.vavr.api.VavrAssertions.assertThat;

class PlayerValidatorTest {

    private final Player player = Player.builder()
            .nickname("NickName")
            .tokens(4)
            .deck(Deck.empty())
            .build();

    @Test
    void should_valid_player() {
        val actual = PlayerValidator.validate(player);
        assertThat(actual).containsValidSame(player);
    }

    @Test
    void should_not_validate_if_blank_nickname() {
        val actual = PlayerValidator.validate(player.withNickname("    "));
        assertThat(actual).containsInvalidInstanceOf(ApplicationError.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1,-2,-10})
    void should_not_validate_with_negative_tokens(int negatives) {
        val actual = PlayerValidator.validate(player.withTokens(negatives));
        assertThat(actual).containsInvalidInstanceOf(ApplicationError.class);
    }

    @Test
    void should_not_validate_without_deck() {
        val actual = PlayerValidator.validate(player.withDeck(null));
        assertThat(actual).containsInvalidInstanceOf(ApplicationError.class);
    }

}