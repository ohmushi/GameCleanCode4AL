import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Examples inspired from
 * <a href="https://www.baeldung.com/vavr">Baeldung Vavr</a> :
 *
 * Option, Try, Functional Interfaces,
 * Collections, Validation, Lazy, Pattern Matching
 */
public class VavrTest {
    @Test
    void optionShouldBeEmptyWhenInitializedWithNull() {
        final var initializedWithNull = Option.of(null);
        assertFalse(initializedWithNull.isEmpty());
    }

    @Test
    void tryShouldPreventExceptions() {
        final var divideByZero = Try.of(() -> 5 / 0);
        assertTrue(divideByZero.isFailure());
    }

    @Test
    void tryShouldOfferDefaultValueWhenFailure() {
        final var failure = Try.of(() -> 5 / 0);
        final var contextualizedDefaultValue = 10;
        assertEquals(
                contextualizedDefaultValue,
                failure.getOrElse(contextualizedDefaultValue)
        );
    }

    // TODO tests Functional Interfaces, Collections, Validation, Lazy, Pattern Matching

}
