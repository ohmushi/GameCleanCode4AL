package clean.code.domain.functional.model;


import lombok.*;

@With
@Value
@Builder
@ToString
@EqualsAndHashCode
public class Hero {
    String name;
    int hp;
    int xp = 0;
    int power;
    int armor;
    Speciality speciality;
    Rarity rarity;
    int level = 1;
}
