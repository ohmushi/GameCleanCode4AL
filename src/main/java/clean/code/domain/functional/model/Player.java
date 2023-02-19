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
@EqualsAndHashCode(of = "id") //TODO remove of = id
public class Player {
    @Builder.Default
    UUID id = UUID.randomUUID();
    String nickname;
    int tokens;
    Deck deck;

    public Player addHeroesInDeck(List<Hero> heroes) {
        return this.withDeck(deck.addHeroes(Array.ofAll(heroes).toJavaList()));
    }
}
