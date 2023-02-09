package clean.code.domain.functional.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@With
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@ToString
@Value
public final class Deck {

    public static Deck empty() {
        return new Deck();
    }

    boolean isEmpty() {
        return true;
    }
}
