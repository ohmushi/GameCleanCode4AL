package clean.code.domain.functional.model;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

import java.util.UUID;

@With
@Value
@Builder
@EqualsAndHashCode
public class Card {
    @Builder.Default
    UUID id = UUID.randomUUID();

    String name;

    int hp;

    @Builder.Default
    int xp = 0;

    int power;

    int armor;

    String speciality;

    String rarity;

    @Builder.Default
    int level = 1;

    public static Card fromHero(Hero hero) {
        return Card.builder()
            .name(hero.getName())
            .hp(hero.getHp())
            .power(hero.getPower())
            .armor(hero.getArmor())
            .speciality(hero.getSpeciality())
            .rarity(hero.getRarity())
            .build();
    }
}
