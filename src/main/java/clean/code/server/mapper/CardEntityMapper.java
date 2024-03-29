package clean.code.server.mapper;

import clean.code.domain.functional.model.Card;
import clean.code.domain.functional.model.Hero;
import clean.code.server.entity.CardEntity;
import clean.code.server.entity.HeroEntity;

import java.util.UUID;

public interface CardEntityMapper {

    static Card toDomain(CardEntity entity) {
        return Card.builder()
                .id(UUID.fromString(entity.getId()))
                .name(entity.getName())
                .hp(entity.getHp())
                .xp(entity.getXp())
                .power(entity.getPower())
                .armor(entity.getArmor())
                .speciality(entity.getSpeciality())
                .rarity(entity.getRarity())
                .level(entity.getLevel())
                .history(entity.getHistory().stream().map(FightResultEntityMapper::toDomain).toList())
                .playerId(entity.getPlayerId())
                .build();
    }

    static CardEntity toCardEntity(Card domain) {
        return CardEntity.builder()
                .id(domain.getId().toString())
                .name(domain.getName())
                .hp(domain.getHp())
                .xp(domain.getXp())
                .power(domain.getPower())
                .armor(domain.getArmor())
                .speciality(domain.getSpeciality())
                .rarity(domain.getRarity())
                .level(domain.getLevel())
                .history(domain.getHistory().stream().map(FightResultEntityMapper::toEntity).toList())
                .playerId(domain.getPlayerId())
                .build();
    }
}
