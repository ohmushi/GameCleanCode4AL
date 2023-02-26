package clean.code.client.mapper;

import clean.code.client.dto.FightResultDto;
import clean.code.domain.functional.model.FightResult;

public interface FightResultDtoMapper {
    static FightResultDto toDto(FightResult fightResult) {
        return new FightResultDto(CardDtoMapper.toDto(fightResult.getOpponent()), fightResult.isWon());
    }
}
