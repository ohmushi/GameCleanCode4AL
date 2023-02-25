package main.java.clean.code.client.rest.mapper;

import main.java.clean.code.client.rest.dto.HeroCreationDto;
import main.java.clean.code.client.rest.dto.HeroDto;
import main.java.clean.code.domain.functional.model.Hero;

public interface HeroDtoMapper {

    static HeroDto toDto(Hero hero) {
        return new HeroDto(
            hero.getId(),
            hero.getName(),
            hero.getHp(),
            hero.getPower(),
            hero.getArmor(),
            hero.getSpeciality(),
            hero.getRarity()
        );
    }

    static Hero heroCreationToDomain(HeroCreationDto dto) {
        return Hero.builder()
            .name(dto.name())
            .hp(dto.hp())
            .power(dto.power())
            .armor(dto.armor())
            .speciality(dto.speciality())
            .rarity(dto.rarity())
            .build();
    }
}
