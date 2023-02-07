package clean.code.client.dto;

import java.util.UUID;

public record HeroDto(
    UUID id,
    String name,
    int hp,
    int xp,
    int power,
    int armor,
    String speciality,
    String rarity,
    int level
) { }
