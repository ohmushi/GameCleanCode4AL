package clean.code.domain.functional.model;


import lombok.*;

import java.util.UUID;

@With
@Value
@Builder
@EqualsAndHashCode
public class Hero {
    @Builder.Default
    UUID id = UUID.randomUUID();

    String name;

    int hp;

    int power;

    int armor;

    String speciality;

    String rarity;
}
