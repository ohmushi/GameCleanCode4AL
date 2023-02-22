package clean.code.client.mapper;

import clean.code.client.dto.HeroCreationDto;
import clean.code.client.dto.HeroDto;
import clean.code.domain.functional.model.Hero;

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
