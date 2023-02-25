package main.java.clean.code.client.rest.dto;

public record HeroCreationDto(
    String name,
    int hp,
    int power,
    int armor,
    String speciality,
    String rarity
) {}
