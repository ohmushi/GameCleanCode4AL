package clean.code.server.mapper;

import main.java.clean.code.domain.functional.model.Deck;
import main.java.clean.code.domain.functional.model.Player;
import clean.code.server.entity.PlayerEntity;

import java.util.UUID;

public interface PlayerEntityMapper {

    static Player toDomain(PlayerEntity entity) {
        return Player.builder()
                .id(UUID.fromString(entity.getId()))
                .nickname(entity.getNickname())
                .tokens(entity.getTokens())
                .deck(new Deck(entity.getCards().stream().map(CardEntityMapper::toDomain).toList()))
                .build();
    }

    static PlayerEntity toEntity(Player domain) {
        return PlayerEntity.builder()
                .id(domain.getId().toString())
                .nickname(domain.getNickname())
                .tokens(domain.getTokens())
                .cards(domain.getDeck().getCards().stream().map(CardEntityMapper::toCardEntity).toList())
                .build();
    }
}
