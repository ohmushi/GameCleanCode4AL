package clean.code.client.mapper;

import clean.code.client.dto.CardDto;
import clean.code.client.dto.HeroCreationDto;
import clean.code.client.dto.HeroDto;
import main.java.clean.code.domain.functional.model.Card;
import main.java.clean.code.domain.functional.model.Hero;

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
