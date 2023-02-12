package clean.code.domain.functional.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@With
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Player {
    @Builder.Default
    UUID id = UUID.randomUUID();
    String nickname;
    int tokens;
    Deck deck;
}
