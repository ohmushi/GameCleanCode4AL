package clean.code.domain.functional.service.validation;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Card;
import lombok.val;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.vavr.api.VavrAssertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CardOpponentValidatorTest {

    private final Card card = Card.builder()
            .level(5)
            .build();

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5})
    void should_validate(int level) {
        val actual = CardOpponentValidator.validate(card, level);
        assertThat(actual).containsValidSame(card);
    }

    @ParameterizedTest
    @ValueSource(ints = {6, 7, 10})
    void should_not_validate_if_invalid_level(int level) {
        val actual = CardOpponentValidator.validate(card, level);
        assertThat(actual).containsInvalidInstanceOf(ApplicationError.class);
    }
}