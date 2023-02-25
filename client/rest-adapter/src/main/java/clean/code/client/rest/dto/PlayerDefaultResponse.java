package clean.code.client.rest.dto;

import java.util.List;

public record PlayerDefaultResponse(
        String id
        , String nickname
        , Integer tokens
        , List<CardDto> deck
        ) {
}
