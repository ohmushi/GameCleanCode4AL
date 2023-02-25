package main.java.clean.code.domain.functional.model;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

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
