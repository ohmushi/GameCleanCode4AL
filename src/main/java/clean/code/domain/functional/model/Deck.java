package clean.code.domain.functional.model;

import io.vavr.collection.Seq;
import lombok.*;
import lombok.experimental.FieldDefaults;

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

    public Deck addHeroes(Seq<Hero> heroes) {
        return this.withHeroes(heroes.appendAll(this.getHeroes()).toJavaList());
    }
}
