package clean.code.server.mapper;

import main.java.clean.code.domain.functional.model.Hero;
import clean.code.server.entity.CardEntity;
import clean.code.server.entity.HeroEntity;

import java.util.UUID;

public interface HeroEntityMapper {

    static Hero toDomain(HeroEntity entity) {
        return Hero.builder()
                .id(UUID.fromString(entity.getId()))
                .name(entity.getName())
                .hp(entity.getHp())
                .power(entity.getPower())
                .armor(entity.getArmor())
                .speciality(entity.getSpeciality())
                .rarity(entity.getRarity())
                .build();
    }

    static HeroEntity toHeroEntity(Hero domain) {
        return HeroEntity.builder()
                .id(domain.getId().toString())
                .name(domain.getName())
                .hp(domain.getHp())
                .power(domain.getPower())
                .armor(domain.getArmor())
                .speciality(domain.getSpeciality())
                .rarity(domain.getRarity())
                .build();
    }
}
