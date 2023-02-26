package clean.code.server.mapper;

import clean.code.domain.functional.model.FightResult;
import clean.code.server.entity.FightResultEntity;

public interface FightResultEntityMapper {
    static FightResult toDomain(FightResultEntity entity) {
        return FightResult.builder()
                .opponent(CardEntityMapper.toDomain(entity.getOpponent()))
                .won(entity.isWon())
                .build();
    }

    static FightResultEntity toEntity(FightResult domain) {
        return FightResultEntity.builder()
                .opponent(CardEntityMapper.toCardEntity(domain.getOpponent()))
                .won(domain.isWon())
                .build();
    }
}
