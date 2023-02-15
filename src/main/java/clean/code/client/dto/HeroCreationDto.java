package clean.code.client.dto;

public record HeroCreationDto(
    String name,
    int hp,
    int power,
    int armor,
    String speciality,
    String rarity
) {}
