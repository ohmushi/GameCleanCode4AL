package clean.code.client.rest.mapper;

import clean.code.client.rest.dto.PlayerDefaultResponse;
import clean.code.client.rest.dto.PlayerRegistrationRequest;
import clean.code.client.rest.dto.PlayerSearchResponse;
import clean.code.domain.functional.model.Player;

public interface PlayerDtoMapper {

    static Player toDomain(PlayerRegistrationRequest request) {
        return Player.builder()
            .nickname(request.nickname())
            .build();
    }

    static PlayerDefaultResponse toDefaultResponse(Player player) {
        return new PlayerDefaultResponse(
            player.getId().toString(),
            player.getNickname(),
            player.getTokens(),
            player.getDeck().getCards().stream().map(CardDtoMapper::toDto).toList()
        );
    }

    static PlayerSearchResponse toSearchResponse(Player player) {
        return new PlayerSearchResponse(
            player.getId().toString(),
            player.getNickname()
        );
    }
}
