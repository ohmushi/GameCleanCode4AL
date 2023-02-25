package clean.code.server.mongo.mapper;

import clean.code.server.mongo.entity.PlayerEntity;
import clean.code.domain.functional.model.Deck;
import clean.code.domain.functional.model.Player;

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
