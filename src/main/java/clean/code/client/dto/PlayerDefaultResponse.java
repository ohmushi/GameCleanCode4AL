package clean.code.client.dto;

import java.util.List;

public record PlayerDefaultResponse(
        String id
        , String nickname
        , Integer tokens
        , List<HeroDto> deck
        ) {
}
