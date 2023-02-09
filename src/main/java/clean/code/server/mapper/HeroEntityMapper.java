package clean.code.server.mapper;

import clean.code.domain.functional.model.Hero;
import clean.code.domain.functional.model.Rarity;
import clean.code.domain.functional.model.Speciality;
import clean.code.server.entity.HeroEntity;

public interface HeroEntityMapper {

    static Hero toDomain(HeroEntity entity) {
        return Hero.builder()
                .id(entity.getId())
                .name(entity.getName())
                .hp(entity.getHp())
                .xp(entity.getXp())
                .power(entity.getPower())
                .armor(entity.getArmor())
                .speciality(Speciality.valueOf(entity.getSpeciality()))
                .rarity(Rarity.valueOf(entity.getRarity()))
                .level(entity.getLevel())
                .build();
    }

    static HeroEntity toEntity(Hero domain) {
        return HeroEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .hp(domain.getHp())
                .xp(domain.getXp())
                .power(domain.getPower())
                .armor(domain.getArmor())
                .speciality(domain.getSpeciality().name())
                .rarity(domain.getRarity().name())
                .level(domain.getLevel())
                .build();
    }
}
