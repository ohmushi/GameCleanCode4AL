package clean.code.domain;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@With
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public final class Hero {
    String name;
    int hp;
    int xp;
    int power;
    Armor armor;
    Speciality speciality;
    Rarity rarity;
    int level;
}
