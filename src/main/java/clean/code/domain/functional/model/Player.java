package clean.code.domain.functional.model;

import io.vavr.collection.Array;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Getter
@With
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Player {
    @Builder.Default
    UUID id = UUID.randomUUID();
    String nickname;
    int tokens;
    Deck deck;
    @Builder.Default
    Integer winCount = 0;

    public Player addCardsInDeck(List<Card> heroes) {
        return this.withDeck(deck.addCards(Array.ofAll(heroes).toJavaList()));
    }
}
