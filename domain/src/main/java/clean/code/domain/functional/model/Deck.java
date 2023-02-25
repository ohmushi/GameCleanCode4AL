package clean.code.domain.functional.model;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.With;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@With
@AllArgsConstructor
@Value
public class Deck {

    List<Card> cards;

    public static Deck empty() {
        return new Deck(Collections.emptyList());
    }

    public Deck addCards(List<Card> cards) {
        final var mutable = new ArrayList<>(this.cards);
        mutable.addAll(cards);
        return this.withCards(mutable);
    }
}
