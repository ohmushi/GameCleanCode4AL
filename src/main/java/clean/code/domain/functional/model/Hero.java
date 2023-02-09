package clean.code.domain.functional.model;


import lombok.*;

import java.util.UUID;

@With
@Value
@Builder
@ToString
@EqualsAndHashCode
public class Hero {
    @Builder.Default
    UUID id = UUID.randomUUID();

    String name;

    int hp;

    @Builder.Default
    int xp = 0;

    int power;

    int armor;

    Speciality speciality;

    Rarity rarity;

    @Builder.Default
    int level = 1;
}
