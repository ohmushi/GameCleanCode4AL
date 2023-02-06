package clean.code.domain.functional.model;


import lombok.*;

import java.util.UUID;

@With
@Value
@Builder
@ToString
@EqualsAndHashCode
public class Hero {
    UUID id = UUID.randomUUID();
    String name;
    int hp;
    int xp = 0;
    int power;
    int armor;
    Speciality speciality;
    Rarity rarity;
    int level = 1;
}
