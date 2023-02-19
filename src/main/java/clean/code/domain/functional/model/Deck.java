package clean.code.domain.functional.model;

import io.vavr.collection.Seq;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@With
@AllArgsConstructor
@Value
public class Deck {

    List<Hero> heroes;

    public static Deck empty() {
        return new Deck(Collections.emptyList());
    }

    public Deck addHeroes(List<Hero> heroes) {
        final var mutable = new ArrayList<>(this.heroes);
        mutable.addAll(heroes);
        return this.withHeroes(mutable);
    }
}
