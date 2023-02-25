package clean.code.client.rest.mapper;

import clean.code.client.rest.dto.CardDto;
import clean.code.domain.functional.model.Card;

public interface CardDtoMapper {

    static CardDto toDto(Card card) {
        return new CardDto(
            card.getId(),
            card.getName(),
            card.getHp(),
            card.getXp(),
            card.getPower(),
            card.getArmor(),
            card.getSpeciality(),
            card.getRarity(),
            card.getLevel()
        );
    }
}
