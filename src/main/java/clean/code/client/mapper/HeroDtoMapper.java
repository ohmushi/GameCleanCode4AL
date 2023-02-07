package clean.code.client.mapper;

import clean.code.client.dto.HeroCreationDto;
import clean.code.client.dto.HeroDto;
import clean.code.domain.functional.model.Hero;
import clean.code.domain.functional.model.Rarity;
import clean.code.domain.functional.model.Speciality;

public interface HeroDtoMapper {

    static HeroDto toDto(Hero hero) {
        return new HeroDto(
            hero.getId(),
            hero.getName(),
            hero.getHp(),
            hero.getXp(),
            hero.getPower(),
            hero.getArmor(),
            hero.getSpeciality().name(),
            hero.getRarity().name(),
            hero.getLevel()
        );
    }

    static Hero heroCreationToDomain(HeroCreationDto dto) {
        return Hero.builder()
            .name(dto.name())
            .hp(dto.hp())
            .power(dto.power())
            .armor(dto.armor())
            .speciality(Speciality.valueOf(dto.speciality()))
            .rarity(Rarity.valueOf(dto.rarity()))
            .build();
    }
}
